package moviesmovies.com.moviesmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.activities.MovieDetailsActivity;
import moviesmovies.com.moviesmovies.interfaces.RecyclerViewClickListener;
import moviesmovies.com.moviesmovies.models.Movie;
import moviesmovies.com.moviesmovies.utils.FavoriteMoviesDatabase;

/**
 * Created by Bilel Tabakh.
 */

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.ViewHolder>{

    private List<Movie> movieList;
    private Context context;
    private Handler handler;
    private LinearLayout emptyFavouritesLinearLayout;

    public FavoritesRecyclerViewAdapter(Context context, List<Movie> movieList, LinearLayout emptyFavouritesLinearLayout){
        this.context = context;
        this.movieList = movieList;
        this.emptyFavouritesLinearLayout = emptyFavouritesLinearLayout;
        this.handler = new Handler();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_favorites, parent, false);
        return new FavoritesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoritesRecyclerViewAdapter.ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.yearTextView.setText(movie.getYear());
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

        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.removeMovie(movie);
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
        TextView titleTextView;
        TextView genreTextView;
        TextView yearTextView;
        ImageButton deleteImageButton;

        private RecyclerViewClickListener listener;

        ViewHolder(View itemView) {
            super(itemView);
            backgroundImageView = itemView.findViewById(R.id.backgroundImageView);
            coverImageView = itemView.findViewById(R.id.coverImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
            deleteImageButton = itemView.findViewById(R.id.deleteImageButton);
            itemView.setOnClickListener(this);
        }

        void setClickListener(RecyclerViewClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        void removeMovie(final Movie movie){
            // run the sentence in a new thread
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FavoriteMoviesDatabase.getDatabase(context).movieDao().delete(movie);
                    movieList.remove(movie);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(movieList.isEmpty()){
                                emptyFavouritesLinearLayout.setVisibility(View.VISIBLE);
                            }
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), movieList.size());
                        }
                    });
                }
            }).start();
        }
    }
}
