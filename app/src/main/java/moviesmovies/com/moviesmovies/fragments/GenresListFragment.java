package moviesmovies.com.moviesmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.adapters.GenresRecyclerViewAdapter;
import moviesmovies.com.moviesmovies.models.Genre;

/**
 * Created by Bilel Tabakh.
 */

public class GenresListFragment extends Fragment {

    private RecyclerView genresRecyclerView;

    public GenresListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres_list, container, false);
        genresRecyclerView = (RecyclerView) view.findViewById(R.id.genresRecyclerView);
        GenresRecyclerViewAdapter adapter = new GenresRecyclerViewAdapter(getActivity(), Genre.getGenres(getActivity()));
        genresRecyclerView.setAdapter(adapter);
        return view;
    }
}
