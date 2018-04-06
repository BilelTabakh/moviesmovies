package moviesmovies.com.moviesmovies.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.adapters.CastRecyclerViewAdapter;
import moviesmovies.com.moviesmovies.adapters.ScreenshotsRecyclerViewAdapter;
import moviesmovies.com.moviesmovies.adapters.SimilarMoviesRecyclerViewAdapter;
import moviesmovies.com.moviesmovies.interfaces.Requests;
import moviesmovies.com.moviesmovies.models.Movie;
import moviesmovies.com.moviesmovies.models.MovieDetails;
import moviesmovies.com.moviesmovies.models.MovieDetailsResponse;
import moviesmovies.com.moviesmovies.models.MoviesResponse;
import moviesmovies.com.moviesmovies.utils.FavoriteMoviesDatabase;
import moviesmovies.com.moviesmovies.utils.Helper;
import moviesmovies.com.moviesmovies.utils.InternetChecker;
import moviesmovies.com.moviesmovies.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private ImageView bannerImageView;
    private ImageView coverImageView;
    private ImageButton playTrailerImageButton;
    private FloatingActionButton floatingActionButton;
    private LinearLayout movieDetailsLinearLayout;
    private ProgressBar progressBar;
    private CoordinatorLayout providerDetailCoordinatorLayout;

    private RecyclerView screenshotsRecyclerView;
    private TextView sypnosisTextView;
    private RecyclerView castRecyclerView;
    private TextView castTextView;

    private TextView movieNameTextView;
    private TextView movieYearTextView;
    private TextView movieRatingTextView;
    private TextView movieGenreTextView;
    private TextView movieRuntimeTextView;

    private RecyclerView similarMoviesRecyclerView;
    private TextView similarTextView;
    private ProgressBar similarMoviesProgressBar;

    Movie movie;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movie = getIntent().getParcelableExtra("movie");
        setToolbar();

        bannerImageView = (ImageView) findViewById(R.id.bannerImageView);
        coverImageView = (ImageView) findViewById(R.id.coverImageView);
        playTrailerImageButton = (ImageButton) findViewById(R.id.playTrailerImageButton);
        playTrailerImageButton.setOnClickListener(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        movieDetailsLinearLayout = (LinearLayout) findViewById(R.id.movieDetailsLinearLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        providerDetailCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.providerDetailCoordinatorLayout);

        checkIfMovieInDatabase();

        screenshotsRecyclerView = (RecyclerView) findViewById(R.id.screenshotsRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        screenshotsRecyclerView.setLayoutManager(linearLayoutManager);
        castTextView = (TextView) findViewById(R.id.castTextView);
        castRecyclerView = (RecyclerView) findViewById(R.id.castRecyclerView);
        sypnosisTextView = findViewById(R.id.sypnosisTextView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition));
            coverImageView.setTransitionName(movie.getMedium_cover_image());
        }

        Glide.with(this)
                .load(movie.getBackground_image())
                .into(bannerImageView);

        Glide.with(this)
                .load(movie.getMedium_cover_image())
                .into(coverImageView);

        movieNameTextView = (TextView) findViewById(R.id.movieNameTextView);
        movieYearTextView = (TextView) findViewById(R.id.movieYearTextView);
        movieRatingTextView = (TextView) findViewById(R.id.movieRatingTextView);
        movieGenreTextView = (TextView) findViewById(R.id.movieGenreTextView);
        movieRuntimeTextView = (TextView) findViewById(R.id.movieRuntimeTextView);
        similarTextView = (TextView) findViewById(R.id.similarTextView);

        LinearLayoutManager similarMoviesLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        similarMoviesRecyclerView = (RecyclerView) findViewById(R.id.similarMoviesRecyclerView);
        similarMoviesRecyclerView.setLayoutManager(similarMoviesLinearLayoutManager);
        similarMoviesProgressBar = (ProgressBar) findViewById(R.id.similarMoviesProgressBar);

        getMovieDetails(movie.getId());
    }

    @Override
    public void onClick(View v) {
        if(v == playTrailerImageButton){
            Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
            i.putExtra("trailer", movie.getYt_trailer_code());
            startActivity(i);
        } else if(v == floatingActionButton){
            setMovieInDatabase();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        floatingActionButton.setVisibility(View.GONE);
    }

    // Make the API call for the movie details
    private void getMovieDetails(String movieId){
        if(InternetChecker.isConnected(this)){
            progressBar.setVisibility(View.VISIBLE);
            Requests req = RetrofitClient.getClient().create(Requests.class);
            Call<MovieDetailsResponse> call = req.getMovieDetails(movieId);
            call.enqueue(new retrofit2.Callback<MovieDetailsResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieDetailsResponse> call, @NonNull Response<MovieDetailsResponse> response) {
                    if(response.code() == 200 && response.body() != null){
                        try {
                            updateUI(response.body().getDataDetails().getMovieDetails());
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(MovieDetailsActivity.this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<MovieDetailsResponse> call, @NonNull Throwable t) {}
            });
        } else {
            Helper.noInternetResponse(this);
        }
    }

    private void updateUI(MovieDetails movieDetails){
        String[] mediumScreenshots = {movieDetails.getMediumScreenshotImage1(), movieDetails.getMediumScreenshotImage2(), movieDetails.getMediumScreenshotImage3()};
        String[] largeScreenshots = {movieDetails.getLargeScreenshotImage1(), movieDetails.getLargeScreenshotImage2(), movieDetails.getLargeScreenshotImage3()};
        ScreenshotsRecyclerViewAdapter adapter = new ScreenshotsRecyclerViewAdapter(this,mediumScreenshots, largeScreenshots);
        screenshotsRecyclerView.setAdapter(adapter);
        if(movieDetails.getCast() != null){
            castTextView.setVisibility(View.VISIBLE);
            CastRecyclerViewAdapter castAdapter = new CastRecyclerViewAdapter(this, movieDetails.getCast());
            castRecyclerView.setAdapter(castAdapter);
        }
        sypnosisTextView.setText(movieDetails.getDescriptionFull());
        movieNameTextView.setText(movieDetails.getTitle());
        movieGenreTextView.setText(movieDetails.getGenres());
        movieRatingTextView.setText(movieDetails.getRating());
        movieYearTextView.setText(movieDetails.getYear());
        movieRuntimeTextView.setText(getString(R.string.runtime, movieDetails.getRuntime()));
        progressBar.setVisibility(View.GONE);
        movieDetailsLinearLayout.setVisibility(View.VISIBLE);
        getSimilarMovies(movieDetails.getId());
    }

    // Make the API call for the similar movies
    private void getSimilarMovies(String movieId){
        if(InternetChecker.isConnected(this)){
            similarMoviesProgressBar.setVisibility(View.VISIBLE);
            Requests req = RetrofitClient.getClient().create(Requests.class);
            Call<MoviesResponse> call = req.getSimilarMovies(movieId);
            call.enqueue(new retrofit2.Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    if(response.code() == 200 && response.body() != null){
                        try {
                            updateSuggestionsUI(response.body().getData().getMovies());
                        } catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(MovieDetailsActivity.this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {}
            });
        } else {
            Helper.noInternetResponse(this);
        }
    }

    private void updateSuggestionsUI(List<Movie> movieList){
        if(movieList != null && !movieList.isEmpty()){
            similarTextView.setVisibility(View.VISIBLE);
            SimilarMoviesRecyclerViewAdapter adapter = new SimilarMoviesRecyclerViewAdapter(this, movieList);
            similarMoviesRecyclerView.setAdapter(adapter);
        }
        similarMoviesProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuMovieIMDB:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(getString(R.string.imdb_link, movie.getImdb_code())));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Method to set the toolbar
    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle(movie.getTitle());
    }

    // Method to delete the movie from the DB if exists and insert it if not, then update the button icon
    private void setMovieInDatabase(){
        final Handler handler = new Handler();
        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isFavorite){
                    FavoriteMoviesDatabase.getDatabase(MovieDetailsActivity.this).movieDao().delete(movie);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            floatingActionButton.setImageResource(R.drawable.ic_star_border);
                            isFavorite = false;
                            displaySnackbar(getString(R.string.movie_remove_database));
                        }
                    });
                } else {
                    FavoriteMoviesDatabase.getDatabase(MovieDetailsActivity.this).movieDao().insert(movie);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            floatingActionButton.setImageResource(R.drawable.ic_star);
                            isFavorite = true;
                            displaySnackbar(getString(R.string.movie_insert_database));
                        }
                    });
                }
            }
        }).start();
    }

    // Method to check if the movie is in the DB and update the button icon according to the result
    private void checkIfMovieInDatabase(){
        final Handler handler = new Handler();
        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                Movie savedMovie = FavoriteMoviesDatabase.getDatabase(MovieDetailsActivity.this).movieDao().getMovieById(movie.getId());
                if(savedMovie != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            floatingActionButton.setImageResource(R.drawable.ic_star);
                            isFavorite = true;
                            floatingActionButton.setOnClickListener(MovieDetailsActivity.this);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            floatingActionButton.setImageResource(R.drawable.ic_star_border);
                            isFavorite = false;
                            floatingActionButton.setOnClickListener(MovieDetailsActivity.this);
                        }
                    });
                }
            }
        }).start();
    }

    private void displaySnackbar(String message){
        Snackbar.make(providerDetailCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
