package org.example.core.adapter.persistance.dto;

import org.example.core.model.UUID;

// Note: While this add a bit of overhead / complexity it allows us to decouple internal Service UUID representations
// from external representations used for persistance should we have the need to store unique data in any of them
public record UUIDDto(String uuid) {

  public static UUID fromDto(final UUIDDto uuidDto) {
    return UUID.fromString(uuidDto.toString());
  }

  public static UUIDDto toDto(final UUID uuid) {
    return new UUIDDto(uuid.toString());
  }

  @Override
  public String toString() {
    return uuid;
  }
}
