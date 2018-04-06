package moviesmovies.com.moviesmovies.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.adapters.FavoritesRecyclerViewAdapter;
import moviesmovies.com.moviesmovies.models.Movie;
import moviesmovies.com.moviesmovies.utils.FavoriteMoviesDatabase;


public class FavoritesFragment extends Fragment {

    private RecyclerView favoritesRecyclerView;
    private LinearLayout emptyFavouritesLinearLayout;
    private ProgressBar progressBar;
    private FavoritesRecyclerViewAdapter adapter;
    private List<Movie> movieList;
    public FavoritesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesRecyclerView = (RecyclerView) view.findViewById(R.id.favoritesRecyclerView);
        emptyFavouritesLinearLayout = (LinearLayout) view.findViewById(R.id.emptyFavouritesLinearLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        movieList = new ArrayList<>();
        adapter = new FavoritesRecyclerViewAdapter(getActivity(), movieList, emptyFavouritesLinearLayout);
        favoritesRecyclerView.setAdapter(adapter);
        return view;
    }

    private void getFavoritesMovies(){
        final Handler handler = new Handler();
        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                movieList.clear();
                movieList.addAll(FavoriteMoviesDatabase.getDatabase(getActivity()).movieDao().getFavoriteMovies());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(movieList);
                    }
                });
            }
        }).start();
    }

    private void updateUI(List<Movie> movieList){
        if(movieList != null && !movieList.isEmpty()){
            emptyFavouritesLinearLayout.setVisibility(View.GONE);
            favoritesRecyclerView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        } else {
            emptyFavouritesLinearLayout.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavoritesMovies();
    }
}
