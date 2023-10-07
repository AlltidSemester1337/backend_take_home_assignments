package rentalservice.core;

import org.junit.jupiter.api.Test;
import rentalservice.model.Customer;
import rentalservice.model.Movie;
import rentalservice.model.MovieType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentalServiceIT {

    @Test
    void testExistingUseCase() {
        Movie fievelGoesWest = new Movie("Fievel Goes West", MovieType.CHILDREN);
        Customer wylie = new Customer("Wylie Burp");
        RentalService.registerMovieRental(wylie, fievelGoesWest, 2);

        assertEquals("""
                Rental record for Wylie Burp
                \tFievel Goes West\t1.5
                Amount owed is 1.5
                You earned 1 frequent renter points""", RentalService.getCustomerStatement(wylie));
    }
}