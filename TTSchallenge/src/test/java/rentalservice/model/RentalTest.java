package rentalservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static rentalservice.TestConstants.TEST_TITLE;

public class RentalTest {

    @Test
    void givenOneRegularMovieRental_calculatesExpectedCost() {
        assertEquals(2.0, new Rental(new Movie(TEST_TITLE, MovieType.REGULAR), 1).calculateCost());
    }

    @Test
    void givenOneRegularMovieRentalMultipleDaysRate_calculatesExpectedCost() {
        assertEquals(3.5, new Rental(new Movie(TEST_TITLE, MovieType.REGULAR), 3).calculateCost());
    }

    @Test
    void givenOneClassicMovieRental_calculatesExpectedCost() {
        assertEquals(4, new Rental(new Movie(TEST_TITLE, MovieType.CLASSIC), 4).calculateCost());
    }

    @Test
    void givenOneRegularRental_returnsExpectedRewardPoints() {
        assertEquals(1, new Rental(new Movie(TEST_TITLE, MovieType.CHILDREN), 4).getRewardPoints());
    }

    @Test
    void givenOneNewReleaseSingleDayMovieRental_returnsNormalRewardPoint() {
        assertEquals(1, new Rental(new Movie(TEST_TITLE, MovieType.NEW_RELEASE), 1).getRewardPoints());
    }

    @Test
    void givenOneNewReleaseMultipleDaysMovieRental_returnsBonusRewardPoint() {
        assertEquals(2, new Rental(new Movie(TEST_TITLE, MovieType.NEW_RELEASE), 2).getRewardPoints());
    }

    @Test
    void givenOneClassicMovieRentalMultipleDays_calculatesExpectedPoints() {
        assertEquals(4, new Rental(new Movie(TEST_TITLE, MovieType.CLASSIC), 4).getRewardPoints());
    }
}
