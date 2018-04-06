package moviesmovies.com.moviesmovies.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.adapters.AutocompleteCustomArrayAdapter;
import moviesmovies.com.moviesmovies.adapters.ViewPagerAdapter;
import moviesmovies.com.moviesmovies.fragments.GenresListFragment;
import moviesmovies.com.moviesmovies.fragments.MoviesListFragment;
import moviesmovies.com.moviesmovies.interfaces.Requests;
import moviesmovies.com.moviesmovies.models.Movie;
import moviesmovies.com.moviesmovies.models.MoviesResponse;
import moviesmovies.com.moviesmovies.models.OrderBy;
import moviesmovies.com.moviesmovies.utils.Helper;
import moviesmovies.com.moviesmovies.utils.InternetChecker;
import moviesmovies.com.moviesmovies.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private SearchView searchView;
    private AutoCompleteTextView mSearchAutoComplete;

    private FloatingActionButton mainFloatingActionButton;
    private FloatingActionButton favoritesFloatingActionButton;
    private FloatingActionButton advancedSearchFloatingActionButton;
    private FloatingActionButton contactFloatingActionButton;

    private LinearLayout favoritesLayout;
    private LinearLayout advancedSearchLayout;
    private LinearLayout contactLayout;

    private Timer timer;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Animation showMainFloatingButtonAnimation;
    private Animation hideMainFloatingButtonAnimation;
    private Animation hideLayout;
    private Animation showLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition));
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(false);

        mainFloatingActionButton = (FloatingActionButton) findViewById(R.id.mainFloatingActionButton);
        favoritesFloatingActionButton = (FloatingActionButton) findViewById(R.id.favoritesFloatingActionButton);
        advancedSearchFloatingActionButton = (FloatingActionButton) findViewById(R.id.advancedSearchFloatingActionButton);
        contactFloatingActionButton = (FloatingActionButton) findViewById(R.id.contactFloatingActionButton);

        favoritesLayout = (LinearLayout) findViewById(R.id.favoritesLayout);
        advancedSearchLayout = (LinearLayout) findViewById(R.id.advancedSearchLayout);
        contactLayout = (LinearLayout) findViewById(R.id.contactLayout);

        mainFloatingActionButton.setOnClickListener(this);
        favoritesFloatingActionButton.setOnClickListener(this);
        advancedSearchFloatingActionButton.setOnClickListener(this);
        contactFloatingActionButton.setOnClickListener(this);

        showMainFloatingButtonAnimation = AnimationUtils.loadAnimation(this, R.anim.show_button);
        hideMainFloatingButtonAnimation = AnimationUtils.loadAnimation(this, R.anim.hide_button);
        hideLayout = AnimationUtils.loadAnimation(this, R.anim.hide_layout);
        showLayout = AnimationUtils.loadAnimation(this, R.anim.show_layout);

        hideLayout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                contactLayout.setVisibility(View.GONE);
                advancedSearchLayout.setVisibility(View.GONE);
                favoritesLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menuSearch));

        mSearchAutoComplete = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mSearchAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // User is typing: reset already started timer (if exists)
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String strSearch = s.toString();
                    // User typed: start the timer
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if(strSearch.length() >= 3) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        searchMovie(strSearch);
                                    }
                                });
                            }
                        }
                    }, 600);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragmentPage(MoviesListFragment.newInstance(null, OrderBy.LATEST), getString(R.string.latest));
        viewPagerAdapter.addFragmentPage(new GenresListFragment(), getString(R.string.genres));
        viewPagerAdapter.addFragmentPage(MoviesListFragment.newInstance(null, OrderBy.RATING), getString(R.string.rating));
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(2);
    }

    // Make the API call for the movie search
    private void searchMovie(String queryTerm){
        if(InternetChecker.isConnected(this)){
            swipeRefreshLayout.setRefreshing(true);
            Requests req = RetrofitClient.getClient().create(Requests.class);
            Call<MoviesResponse> call = req.searchMovie(queryTerm);
            call.enqueue(new retrofit2.Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    if(response.code() == 200 && response.body() != null){
                        try {
                            updateUI(response.body().getData().getMovies());
                        } catch (Exception ignored){}
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        } else {
            Helper.noInternetResponse(this.getApplicationContext());
        }
    }

    private void updateUI(List<Movie> movies){
        if(movies != null){
            AutocompleteCustomArrayAdapter adapter = new AutocompleteCustomArrayAdapter(this, R.layout.item_search_autocomplete, movies);
            adapter.getFilter().filter(null);
            mSearchAutoComplete.setAdapter(adapter);
            mSearchAutoComplete.showDropDown();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == mainFloatingActionButton){
            floatingButtonActive(contactLayout.getVisibility() == View.VISIBLE &&
                                advancedSearchLayout.getVisibility() == View.VISIBLE &&
                                favoritesLayout.getVisibility() == View.VISIBLE);
        }
        else if(v == favoritesFloatingActionButton){
            Intent intent = new Intent(MainActivity.this, FragmentHolderActivity.class);
            intent.putExtra("display", "favorites");
            startActivity(intent);
        } else if(v == contactFloatingActionButton){
            String mailto = getString(R.string.mail_to,Uri.encode(getString(R.string.app_name))) ;
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse(mailto));
            try {
                startActivity(emailIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "No sending mail app found", Toast.LENGTH_SHORT).show();
            }
        } else if(v == advancedSearchFloatingActionButton){
            Intent intent = new Intent(MainActivity.this, FragmentHolderActivity.class);
            intent.putExtra("display", "search");
            startActivity(intent);
        }
    }

    // Setting the floating buttons visibility and animations
    private void floatingButtonActive(boolean isActive){
        if(isActive){
            contactLayout.startAnimation(hideLayout);
            advancedSearchLayout.startAnimation(hideLayout);
            favoritesLayout.startAnimation(hideLayout);
            mainFloatingActionButton.startAnimation(hideMainFloatingButtonAnimation);
        } else {
            contactLayout.setVisibility(View.VISIBLE);
            advancedSearchLayout.setVisibility(View.VISIBLE);
            favoritesLayout.setVisibility(View.VISIBLE);
            contactLayout.startAnimation(showLayout);
            advancedSearchLayout.startAnimation(showLayout);
            favoritesLayout.startAnimation(showLayout);
            mainFloatingActionButton.startAnimation(showMainFloatingButtonAnimation);
        }
    }
}
