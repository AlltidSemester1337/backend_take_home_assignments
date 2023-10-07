package org.example.core;

import static org.example.core.UUIDTestUtils.parseDateFromUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.example.core.adapter.persistance.UUIDRepository;
import org.example.core.adapter.persistance.dto.UUIDDto;
import org.example.core.model.UUID;
import org.junit.jupiter.api.Test;

// Note: Can be modified to do performance / manual exploratory testing if needed
public class UUIDServiceIT {

  @Test
  void testServiceSynchronousWritesAndAuditRead() {
    final UUIDService uuidService = new UUIDServiceImpl(new UUIDRepositoryImpl());
    uuidService.generateAndSaveUUID();
    uuidService.generateAndSaveUUID();
    uuidService.generateAndSaveUUID();

    final LocalDate today = LocalDate.now();
    final Set<UUID> uuidsForToday = uuidService.getAllIdsForDate(today);
    assertEquals(3, uuidsForToday.size());
    uuidsForToday.forEach(uuid -> assertEquals(today, parseDateFromUUID(uuid)));
  }

  //@Test
  //void testServiceAsynchronousWritesAndAuditRead() {
  //     expectedUUIDsForToday.add(uuidService.generateUUID());
  //    expectedUUIDsForToday.add(uuidService.generateUUID());
  //    expectedUUIDsForToday.add(uuidService.shutDown());

  // Note: Shell trivial implementation, main purpose to facilitate testing of new better implementations of persistance.
  private class UUIDRepositoryImpl implements UUIDRepository {

    final ConcurrentHashMap<UUIDDto, LocalDate> uuidToDateMap = new ConcurrentHashMap<>();

    @Override
    public void saveUUID(final UUIDDto uuid) {
      // Note: LocalDate.now() here is just to provide a trivial example as this persistance is not so interesting anyway.
      // This will have a chance of failing close to 00:00:00 (crossing date boundary) so it's not a ideal test
      uuidToDateMap.put(uuid, LocalDate.now());
    }

    @Override
    public Set<UUIDDto> getAllIdsForDate(final LocalDate date) {
      return uuidToDateMap.entrySet().stream()
          .filter(uuidLocalDateEntry -> uuidLocalDateEntry.getValue().equals(date))
          .map(Entry::getKey)
          .collect(Collectors.toSet());
    }
  }
}
