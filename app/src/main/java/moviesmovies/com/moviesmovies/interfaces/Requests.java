package moviesmovies.com.moviesmovies.interfaces;

import moviesmovies.com.moviesmovies.models.MovieDetailsResponse;
import moviesmovies.com.moviesmovies.models.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bilel Tabakh.
 */

public interface Requests {
    // API To fetch movies
    @GET("list_movies.json")
    Call<MoviesResponse> getMoviesList(
                        @Query("genre") String genre,
                        @Query("sort_by") String sortBy,
                        @Query("page") int page);

    // API to search movies
    @GET("list_movies.json")
    Call<MoviesResponse> searchMovie(@Query("query_term") String queryTerm);

    // API To fetch movie details
    @GET("movie_details.json?with_cast=true&with_images=true")
    Call<MovieDetailsResponse> getMovieDetails(@Query("movie_id") String id);

    // API to get similar movies
    @GET("movie_suggestions.json")
    Call<MoviesResponse> getSimilarMovies(@Query("movie_id") String id);
}
