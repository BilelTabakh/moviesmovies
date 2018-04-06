package moviesmovies.com.moviesmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.ActivityOptionsCompat;

import com.bumptech.glide.Glide;

import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.activities.MovieDetailsActivity;
import moviesmovies.com.moviesmovies.interfaces.RecyclerViewClickListener;
import moviesmovies.com.moviesmovies.models.Movie;

/**
 * Created by Bilel Tabakh.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>{

    private List<Movie> movieList;
    private Context context;

    public MoviesRecyclerViewAdapter(Context context, List<Movie> movieList){
        this.context = context;
        this.movieList = movieList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_movies, parent, false);
        return new MoviesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesRecyclerViewAdapter.ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(movie.getYear());
        holder.ratingTextView.setText(movie.getRating());
        holder.runtimeTextView.setText(context.getString(R.string.runtime, movie.getRuntime()));
        holder.genreTextView.setText(movie.getStringGenres());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.coverImageView.setTransitionName(movie.getMedium_cover_image());
        }
        holder.setClickListener(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(context, MovieDetailsActivity.class);
                i.putExtra("movie", movie);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.coverImageView, movie.getMedium_cover_image());
                    context.startActivity(i, options.toBundle());
                } else {
                    context.startActivity(i);
                }
            }
        });

        loadImages(movie, holder);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private void loadImages(Movie movie, ViewHolder holder){
        Glide.with(context)
                .load(movie.getBackground_image())
                .into(holder.backgroundImageView);

        Glide.with(context)
                .load(movie.getMedium_cover_image())
                .placeholder(R.drawable.cover_placeholder)
                .into(holder.coverImageView);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView backgroundImageView;
        ImageView coverImageView;
        TextView runtimeTextView;
        TextView titleTextView;
        TextView genreTextView;
        TextView ratingTextView;
        TextView yearTextView;

        private RecyclerViewClickListener listener;

        ViewHolder(View itemView) {
            super(itemView);
            backgroundImageView = itemView.findViewById(R.id.backgroundImageView);
            coverImageView = itemView.findViewById(R.id.coverImageView);
            runtimeTextView = itemView.findViewById(R.id.runtimeTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            itemView.setOnClickListener(this);
        }

        void setClickListener(RecyclerViewClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

}
