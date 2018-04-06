package moviesmovies.com.moviesmovies.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import moviesmovies.com.moviesmovies.models.Movie;

/**
 * Created by Bilel Tabakh.
 */

@Dao
public interface MovieDao {
    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("SELECT * FROM movie")
    List<Movie> getFavoriteMovies();

    @Query("SELECT * FROM movie WHERE id=:movieId")
    Movie getMovieById(String movieId);
}
