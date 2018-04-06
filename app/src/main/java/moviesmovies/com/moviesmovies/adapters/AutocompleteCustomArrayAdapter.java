package moviesmovies.com.moviesmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.activities.MainActivity;
import moviesmovies.com.moviesmovies.activities.MovieDetailsActivity;
import moviesmovies.com.moviesmovies.models.Movie;

/**
 * Created by Bilel Tabakh.
 */

public class AutocompleteCustomArrayAdapter extends ArrayAdapter<Movie> {

    private int layoutResourceId;
    private Context context;
    private List<Movie> movieList;

    public AutocompleteCustomArrayAdapter(Context context, int layoutResourceId, List<Movie> movieList){
        super(context, layoutResourceId, movieList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            // inflate the layout
            LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        try {
            final Movie movie = movieList.get(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
            titleTextView.setText(movie.getTitle());
            TextView yearTextView = (TextView) convertView.findViewById(R.id.yearTextView);
            yearTextView.setText(movie.getYear());
            ImageView coverImageView = (ImageView) convertView.findViewById(R.id.coverImageView);
            Glide.with(context)
                    .load(movie.getSmall_cover_image())
                    .placeholder(R.drawable.cover_placeholder)
                    .into(coverImageView);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MovieDetailsActivity.class);
                    i.putExtra("movie", movie);
                    context.startActivity(i);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
