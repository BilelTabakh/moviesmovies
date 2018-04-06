package moviesmovies.com.moviesmovies.utils;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.activities.MovieDetailsActivity;
import moviesmovies.com.moviesmovies.interfaces.MovieDao;
import moviesmovies.com.moviesmovies.models.Movie;

import static moviesmovies.com.moviesmovies.R.id.floatingActionButton;

/**
 * Created by Bilel Tabakh.
 */

@Database(entities = {Movie.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class FavoriteMoviesDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
    private static FavoriteMoviesDatabase INSTANCE;

    public static FavoriteMoviesDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavoriteMoviesDatabase.class, "movie-database").build();
        }
        return INSTANCE;
    }
}
