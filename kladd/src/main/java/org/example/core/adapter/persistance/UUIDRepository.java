package org.example.core.adapter.persistance;

import java.time.LocalDate;
import java.util.Set;
import org.example.core.adapter.persistance.dto.UUIDDto;

public interface UUIDRepository {

  void saveUUID(final UUIDDto uuid);

  Set<UUIDDto> getAllIdsForDate(final LocalDate date);

}
