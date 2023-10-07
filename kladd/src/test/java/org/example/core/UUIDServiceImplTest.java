package org.example.core;

import static org.example.core.UUIDTestUtils.parseDateFromUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;
import org.example.core.adapter.persistance.UUIDRepository;
import org.example.core.adapter.persistance.dto.UUIDDto;
import org.example.core.model.UUID;
import org.junit.jupiter.api.Test;

class UUIDServiceImplTest {

  private final UUIDRepository mockRepository = mock(UUIDRepository.class);
  private final UUIDServiceImpl uuidService = new UUIDServiceImpl(mockRepository);

  @Test
  void shouldReturnGeneratedUUIDWithExpectedFormatAndDate() {
    final Clock fixedClock = mock(Clock.class);
    when(fixedClock.millis()).thenReturn(1L);

    final UUID uuid = uuidService.generateAndSaveUUID(fixedClock);
    assertEquals(128, uuid.toString().length());
    final LocalDate expectedDate = LocalDate.ofInstant(Instant.ofEpochMilli(fixedClock.millis()), ZoneId.of("UTC"));
    assertEquals(expectedDate, parseDateFromUUID(uuid));
  }

  @Test
  void givenExactSameTime_shouldStillGenerateUniqueUUIDs() {
    final Clock fixedClock = mock(Clock.class);
    when(fixedClock.millis()).thenReturn(1L);

    final UUID uuid = uuidService.generateAndSaveUUID(fixedClock);
    final UUID anotherUuid = uuidService.generateAndSaveUUID(fixedClock);
    assertNotEquals(uuid, anotherUuid);
  }

  @Test
  void shouldPersistGeneratedUUID() {
    uuidService.generateAndSaveUUID();
    verify(mockRepository).saveUUID(any());
  }

  // Note: Not super useful testcase, mainly for documenting expected behaviour on date boundary values
  // Can be updated in case we want to treat new dates differently for persistance reasons etc
  @Test
  void whenGeneratingUUIDsCloseToNewDate_canReturnUUIDForYesterday() {
    final Clock fixedClock = mock(Clock.class);
    final LocalDateTime dateTimeCloseToNewDate = LocalDateTime.of(2023, 2, 1, 23, 59, 59);
    final long timeStampCloseToNewDate = dateTimeCloseToNewDate.toEpochSecond(ZoneOffset.UTC) * 1000;
    final long timeStampOnNewDate = dateTimeCloseToNewDate.plusSeconds(1).toEpochSecond(ZoneOffset.UTC) * 1000;

    when(fixedClock.millis()).thenReturn(timeStampCloseToNewDate).thenReturn(timeStampOnNewDate);

    final UUID uuid = uuidService.generateAndSaveUUID(fixedClock);

    final LocalDate expectedNewDate = LocalDate.ofInstant(Instant.ofEpochMilli(fixedClock.millis()), ZoneId.of("UTC"));
    assertEquals(expectedNewDate, parseDateFromUUID(uuid).plusDays(1));
  }

  @Test
  void retrievesAllIdsForExpectedDate_AndNoOtherIdsForOtherDates() {
    final LocalDate today = LocalDate.now();
    final LocalDate yesterday = today.minusDays(1);
    final String expectedTodayUUIDString = "00000000000000000000000110000110000011110110110001000100000110000100000101001101111001100001101111111001001111000011010000100000";
    final String expectedYesterdayUUIDString = "00000000000000000000000011000011000001111011011000100100000000000110101100100100111100010110100010011111111010001000011011011110";

    when(mockRepository.getAllIdsForDate(eq(today))).thenReturn(Set.of(new UUIDDto(expectedTodayUUIDString)));
    when(mockRepository.getAllIdsForDate(eq(yesterday))).thenReturn(Set.of(new UUIDDto(expectedYesterdayUUIDString)));

    final Set<UUID> uuidsForToday = uuidService.getAllIdsForDate(today);
    final Set<UUID> uuidsForYesterday = uuidService.getAllIdsForDate(yesterday);
    assertEquals(uuidsForToday.size(), uuidsForYesterday.size());
    assertEquals(1, uuidsForToday.size());
    assertEquals(expectedTodayUUIDString, uuidsForToday.iterator().next().toString());
  }
}