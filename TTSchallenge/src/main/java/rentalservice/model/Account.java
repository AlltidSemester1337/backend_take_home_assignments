package rentalservice.model;


import java.util.ArrayList;
import java.util.List;

public class Account {

    private double debt;
    private int rewardPoints;
    private final List<Rental> rentals = new ArrayList<>();

    public void addRental(Rental rental) {
        rentals.add(rental);
        debt += rental.calculateCost();
        rewardPoints += rental.getRewardPoints();
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public double getDebt() {
        return debt;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }
}
