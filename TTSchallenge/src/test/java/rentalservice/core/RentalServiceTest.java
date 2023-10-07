package rentalservice.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rentalservice.model.Customer;
import rentalservice.model.Movie;
import rentalservice.model.MovieType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static rentalservice.TestConstants.TEST_TITLE;

class RentalServiceTest {
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("testCustomer");
    }

    @Test
    void givenNoRentals_returnsNoAmountAndPoints() {
        String statement = RentalService.getCustomerStatement(customer);
        assertThat(statement, containsString("Rental record for testCustomer"));
        assertThat(statement, containsString("Amount owed is 0"));
        assertThat(statement, containsString("You earned 0 frequent renter points"));
    }

    @Test
    void givenRentals_returnsExpectedOutput() {
        RentalService.registerMovieRental(customer, new Movie(TEST_TITLE, MovieType.NEW_RELEASE), 2);
        RentalService.registerMovieRental(customer, new Movie(TEST_TITLE + "2", MovieType.CHILDREN), 4);
        String statement = RentalService.getCustomerStatement(customer);
        assertThat(statement, containsString("Rental record for testCustomer"));
        assertThat(statement, containsString("testTitle\t6.0\n"));
        assertThat(statement, containsString("testTitle2\t3.0"));
        assertThat(statement, containsString("Amount owed is 9.0"));
        assertThat(statement, containsString("You earned 3 frequent renter points"));
    }

}