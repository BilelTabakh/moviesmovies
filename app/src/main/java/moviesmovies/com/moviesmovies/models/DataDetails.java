package moviesmovies.com.moviesmovies.models;

/**
 * Created by Bilel Tabakh.
 */

public class DataDetails {
    private MovieDetails movie;

    public MovieDetails getMovieDetails () {
        return movie;
    }

    @Override
    public String toString() {
        return "ClassPojo [movie = "+movie+"]";
    }
}
