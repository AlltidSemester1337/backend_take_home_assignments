package org.example.core;

import java.time.LocalDate;
import java.util.Set;
import org.example.core.model.UUID;

public interface UUIDService {

  UUID generateAndSaveUUID();

  // Note: May fit some use cases where client requires guaranteed persistance but does not immediatly require to use the generated UUID
  // and can continue process other tasks meanwhile
  //Future<UUID> generateUUIDAndSaveUUID();

  // Note: If client does not require a guarantee for persisting the UUID we can use this approach instead
  // UUID generateUUID();
  Set<UUID> getAllIdsForDate(final LocalDate date);
}
