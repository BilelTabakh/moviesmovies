package moviesmovies.com.moviesmovies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import moviesmovies.com.moviesmovies.R;

public class AdvancedSearchFragment extends Fragment implements View.OnClickListener{

    private Spinner qualitySpinner;
    private Spinner genreSpinner;
    private Spinner ratingSpinner;
    private Spinner orderBySpinner;
    private AppCompatButton searchButton;

    ArrayAdapter<CharSequence> adapter;

    public AdvancedSearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        qualitySpinner = (Spinner) view.findViewById(R.id.qualitySpinner);
        genreSpinner = (Spinner) view.findViewById(R.id.genreSpinner);
        ratingSpinner = (Spinner) view.findViewById(R.id.ratingSpinner);
        orderBySpinner = (Spinner) view.findViewById(R.id.orderBySpinner);
        searchButton = (AppCompatButton) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.quality_search_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qualitySpinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.genres_search_name, android.R.layout.simple_spinner_item);
        genreSpinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.rating_search_name, android.R.layout.simple_spinner_item);
        ratingSpinner.setAdapter(adapter);

        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.order_search_name, android.R.layout.simple_spinner_item);
        orderBySpinner.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == searchButton){
            //TODO Make the search request
        }
    }
}
