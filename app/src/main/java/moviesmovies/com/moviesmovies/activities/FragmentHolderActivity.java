package moviesmovies.com.moviesmovies.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionInflater;
import android.view.View;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.fragments.AdvancedSearchFragment;
import moviesmovies.com.moviesmovies.fragments.FavoritesFragment;
import moviesmovies.com.moviesmovies.fragments.MoviesListFragment;
import moviesmovies.com.moviesmovies.models.OrderBy;

public class FragmentHolderActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_holder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition));
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String strGenre = intent.getStringExtra("genre");
        String strDisplay = intent.getStringExtra("display");
        setFragment(strDisplay, strGenre);

    }

    private void setFragment(String display, String genre){
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the FrameLayout with new Fragment according to the required one
        switch (display){
            case "genre":
                fragmentTransaction.replace(R.id.fragmentFrameLayout, (Fragment) MoviesListFragment.newInstance(genre, OrderBy.LATEST));
                setTitle(genre);
                break;
            case "favorites":
                setTitle(getString(R.string.my_favorites));
                fragmentTransaction.replace(R.id.fragmentFrameLayout, (Fragment) new FavoritesFragment());
                break;
            case "search":
                setTitle(getString(R.string.advanced_search));
                fragmentTransaction.replace(R.id.fragmentFrameLayout, (Fragment) new AdvancedSearchFragment());
                break;
            default:
                break;

        }
        fragmentTransaction.commit();
    }
}
