package rentalservice.model;

public class Movie {
    private final String title;
    private final MovieType movieType;

    public Movie(String title, MovieType movieType) {
        this.title = title;
        this.movieType = movieType;
    }

    public String getTitle() {
        return title;
    }

    public MovieType getType() {
        return movieType;
    }
}
