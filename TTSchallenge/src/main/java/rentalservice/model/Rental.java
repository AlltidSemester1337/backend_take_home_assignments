package rentalservice.model;


public class Rental {
    private final Movie movie;
    private final int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public double calculateCost() {
        MovieType movieType = getMovie().getType();
        double maxDaysBeforeLongRental = movieType.getMaxDaysBeforeLongRental();

        if (daysRented > maxDaysBeforeLongRental) {
            return movieType.getFixedCost() + (daysRented - maxDaysBeforeLongRental) * movieType.getLongRentalRate();
        }
        return movieType.getFixedCost();
    }

    int getRewardPoints() {
        return switch (getMovie().getType()) {
            case NEW_RELEASE -> {
                if (getMovie().getType() == MovieType.NEW_RELEASE && getDaysRented() > 1)
                    yield 2;
                yield 1;
            }
            case CLASSIC -> getDaysRented();
            default -> 1;
        };

    }


}
