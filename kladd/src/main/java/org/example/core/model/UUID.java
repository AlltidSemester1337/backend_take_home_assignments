package org.example.core.model;

import java.security.SecureRandom;
import java.time.Clock;

// Note: Simple implementation of UUID using only time and randomness on 64 + 64 bits
public record UUID(long mostSignificantBits, long leastSignificantBits) {

  public static UUID randomUUID() {
    return randomUUID(Clock.systemUTC());
  }

  public static UUID randomUUID(final Clock clock) {
    return randomUUID(clock, new SecureRandom());
  }

  public static UUID randomUUID(final Clock clock, final SecureRandom random) {
    final long mostSignificantBits = clock.millis();
    final long leastSignificantBits = random.nextLong();
    return new UUID(mostSignificantBits, leastSignificantBits);
  }

  public static UUID fromString(final String uuidAsString) {
    final long mostSignificantBits = Long.parseLong(uuidAsString, 0, 64, 2);
    final long leastSignificantBits = Long.parseLong(uuidAsString, 64, 128, 2);
    return new UUID(mostSignificantBits, leastSignificantBits);
  }

// Long.toHexString() for less verbose String values
  @Override
  public String toString() {
    final String msbPaddedBinary = String.format("%64s", Long.toBinaryString(mostSignificantBits));
    final String lsbPaddedBinary = String.format("%64s", Long.toBinaryString(leastSignificantBits));
    final String completeUUIDPaddedBinary = msbPaddedBinary + lsbPaddedBinary;

    return completeUUIDPaddedBinary.replace(' ', '0');
  }
}
