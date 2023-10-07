package rentalservice.core;

import rentalservice.model.Account;
import rentalservice.model.Customer;
import rentalservice.model.Movie;
import rentalservice.model.Rental;

import java.util.Collections;
import java.util.List;

public class RentalService {

    public static void registerMovieRental(Customer customer, Movie movie, int days) {
        Account account = customer.getAccount();
        account.addRental(new Rental(movie, days));
    }

    //etc
    public static String getCustomerStatement(Customer customer) {

        StringBuilder result = new StringBuilder("Rental record for " + customer.getName() + "\n");

        List<Rental> rentals = customer.getAccount().getRentals();
        Collections.sort(rentals, (rental, other) -> (int) (rental.calculateCost() - other.calculateCost()));
        int numberOfFreeMovies = rentals.size() % 3;

        List<Rental> nonFreeRentals = rentals.subList(numberOfFreeMovies - 1, rentals.size());

        double totalDebt = 0.0;

        for (Rental rental : nonFreeRentals) {
            double cost = rental.calculateCost();
            totalDebt += cost;

            result.append("\t").append(rental.getMovie().getTitle()).append("\t").append(cost).append("\n");
        }

        Account account = customer.getAccount();
        result.append("Amount owed is ").append(totalDebt).append("\n");
        result.append("You earned ").append(account.getRewardPoints()).append(" frequent renter points");

        return result.toString();
    }

}
