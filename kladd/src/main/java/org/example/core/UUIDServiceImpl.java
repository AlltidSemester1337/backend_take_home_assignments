package org.example.core;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.core.adapter.persistance.UUIDRepository;
import org.example.core.adapter.persistance.dto.UUIDDto;
import org.example.core.model.UUID;

public class UUIDServiceImpl implements UUIDService {

  private final UUIDRepository uuidRepository;
  private final SecureRandom random;

  public UUIDServiceImpl(final UUIDRepository uuidRepository) {
    this.uuidRepository = uuidRepository;
    // Note: Create random here to allow better performance for generating UUIDs using the service
    // while at the same time having periodic re-seeding (whenever a new instance of service is created)
    this.random = new SecureRandom();
  }

  // Note: Dependent to a larger extent on vertical scaling given that persistance can support quick writes,
  // otherwise see other commented service methods below
  @Override
  public UUID generateAndSaveUUID() {
    return generateAndSaveUUID(Clock.systemUTC());
  }

  UUID generateAndSaveUUID(final Clock clock) {
    final UUID uuid = UUID.randomUUID(clock, random);
    uuidRepository.saveUUID(UUIDDto.toDto(uuid));
    return uuid;
  }

  // Note: May fit some use cases where caller does not immediatly require to use the generated UUID
  // but still want guaranteed persistance, this can allow the caller to continue process other tasks meanwhile
  //Future<UUID> generateUUIDAndSaveUUID() {
  // ...
  // return threadPool.submit(() -> uuidRepository.saveUUID());
  //}

  // Note: If client does not require a guarantee for persisting the UUID we can use this approach instead to enable great performance
  // for UUID generation as well as both Horizontal (internal queue) and possibly Vertical (event stream) scaling
  // Main trade-offs for above options is increased complexity vs better fault tolerance on individual nodes
  // UUID generateUUID() {
  // ...
  // UUIDsToBePersistedQueue.add(uuid); (queue within node)
  // alternatively eventStream.publishUUIDToPersist(uuid); (queue shared across multiple nodes)
  // Note: An interesting solution for supporting massive scale frequent writes here could be to rely on eventstream as "hot" persistance on current date
  // And then move events into "cold" archive storage as either continous or nightly job.
  // Otherwise also a traditional master/slave or other replication strategy for persistance should cover all "typical" auditing needs (looking back at least one day)
  // }

  @Override
  public Set<UUID> getAllIdsForDate(final LocalDate date) {
    return uuidRepository.getAllIdsForDate(date).stream()
        .map(UUIDDto::fromDto)
        .collect(Collectors.toSet());
  }
}
