package moviesmovies.com.moviesmovies.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.adapters.MoviesRecyclerViewAdapter;
import moviesmovies.com.moviesmovies.interfaces.Requests;
import moviesmovies.com.moviesmovies.models.Movie;
import moviesmovies.com.moviesmovies.models.MoviesResponse;
import moviesmovies.com.moviesmovies.utils.EndlessRecyclerViewScrollListener;
import moviesmovies.com.moviesmovies.utils.Helper;
import moviesmovies.com.moviesmovies.utils.InternetChecker;
import moviesmovies.com.moviesmovies.utils.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Bilel Tabakh.
 */

public class MoviesListFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView moviesRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Movie> movies;
    private MoviesRecyclerViewAdapter adapter;
    private String strGenre;
    private String strSortBy;
    private EndlessRecyclerViewScrollListener scrollListener;

    public static MoviesListFragment newInstance(String genre, String sortBy) {
        MoviesListFragment moviesListFragment = new MoviesListFragment();
        Bundle args = new Bundle();
        args.putString("genre", genre);
        args.putString("sortBy", sortBy);
        moviesListFragment.setArguments(args);
        return moviesListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        strGenre = bundle.getString("genre");
        strSortBy = bundle.getString("sortBy");
    }

    @Override
    public void onStart() {
        super.onStart();
        scrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager) moviesRecyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getMoviesList(strGenre, strSortBy, page, false, true);
            }
        };
        moviesRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        moviesRecyclerView = (RecyclerView) view.findViewById(R.id.moviesRecyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        movies = new ArrayList<>();
        adapter = new MoviesRecyclerViewAdapter(getActivity(), movies);
        moviesRecyclerView.setAdapter(adapter);
        getMoviesList(strGenre, strSortBy, 1, false, false);
        return view;
    }

    // Make the API call for the movies list
    private void getMoviesList(String genre, String sortBy, int page, final boolean isRefresh, final boolean isScrollDown){
        if(InternetChecker.isConnected(getActivity())){
            swipeRefreshLayout.setRefreshing(true);
            Requests req = RetrofitClient.getClient().create(Requests.class);
            Call<MoviesResponse> call = req.getMoviesList(genre, sortBy, page);
            call.enqueue(new retrofit2.Callback<MoviesResponse>() {
                @Override
                public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                    if(response.code() == 200 && response.body() != null){
                        try {
                            updateUI(response.body().getData().getMovies(), isRefresh, isScrollDown);
                        } catch (Exception ignored){}
                    }
                }
                @Override
                public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                }
            });
        } else {
            Helper.noInternetResponse(getActivity().getApplicationContext());
        }
    }

    private void updateUI(List<Movie> movieList, boolean isRefresh, boolean isScrollDown){
        if(isRefresh){
            movies.clear();
        }
        movies.addAll(movieList);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        if(!isScrollDown){
            moviesRecyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    public void onRefresh() {
        getMoviesList(strGenre, strSortBy, 1, true, false);
    }
}
