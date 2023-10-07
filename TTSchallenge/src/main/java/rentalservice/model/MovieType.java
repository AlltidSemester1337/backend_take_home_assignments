package rentalservice.model;

public enum MovieType {
    CHILDREN(1.5, 3, 1.5),
    REGULAR(2.0, 2, 1.5),
    NEW_RELEASE(0.0, 0, 3.0),
    CLASSIC(0.0, 0, 1.0);

    final double fixedCost;
    final int maxDaysBeforeLongRental;
    final double longRentalRate;

    MovieType(double fixedCost, int maxDaysBeforeLongRental, double longRentalRate) {
        this.fixedCost = fixedCost;
        this.maxDaysBeforeLongRental = maxDaysBeforeLongRental;
        this.longRentalRate = longRentalRate;
    }

    public double getFixedCost() {
        return fixedCost;
    }

    public int getMaxDaysBeforeLongRental() {
        return maxDaysBeforeLongRental;
    }

    public double getLongRentalRate() {
        return longRentalRate;
    }
}
