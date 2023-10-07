import rentalservice.core.RentalService;
import rentalservice.model.Customer;
import rentalservice.model.Movie;
import rentalservice.model.MovieType;

public class Main {

    public static void main(String[] args) {
        Movie fievelGoesWest = new Movie("Fievel Goes West", MovieType.CHILDREN);
        Customer wylie = new Customer("Wylie Burp");
        RentalService.registerMovieRental(wylie, fievelGoesWest, 2);
        String statement = RentalService.getCustomerStatement(wylie);
        System.out.print(statement);
    }

}
