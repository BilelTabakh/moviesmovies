package moviesmovies.com.moviesmovies.models;

import java.util.List;

/**
 * Created by Bilel Tabakh.
 */

public class Data {
    private String limit;
    private List<Movie> movies;
    private String page_number;
    private String movie_count;

    public String getLimit () {
        return limit;
    }

    public List<Movie> getMovies () {
        return movies;
    }

    public String getPageNumber() {
        return page_number;
    }

    public String getMovieCount() {
        return movie_count;
    }

    @Override
    public String toString() {
        return "ClassPojo [limit = "+limit+", movies = "+movies+", page_number = "+page_number+", movie_count = "+movie_count+"]";
    }
}
