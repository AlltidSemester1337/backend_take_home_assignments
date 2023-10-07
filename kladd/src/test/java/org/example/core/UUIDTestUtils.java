package org.example.core;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.example.core.model.UUID;

public class UUIDTestUtils {

  private UUIDTestUtils() {
  }

  public static LocalDate parseDateFromUUID(UUID uuid) {
    return LocalDate.ofInstant(Instant.ofEpochMilli(uuid.mostSignificantBits()), ZoneId.of("UTC"));
  }
}
