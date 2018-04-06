package moviesmovies.com.moviesmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.activities.MovieDetailsActivity;
import moviesmovies.com.moviesmovies.interfaces.RecyclerViewClickListener;
import moviesmovies.com.moviesmovies.models.Movie;

/**
 * Created by Bilel Tabakh.
 */

public class SimilarMoviesRecyclerViewAdapter extends RecyclerView.Adapter<SimilarMoviesRecyclerViewAdapter.ViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public SimilarMoviesRecyclerViewAdapter(Context context, List<Movie> movieList){
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_similar_movies, parent, false);
        return new SimilarMoviesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);

        Glide.with(context)
                .load(movie.getMedium_cover_image())
                .placeholder(R.drawable.cover_placeholder)
                .into(holder.coverImageView);

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
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView coverImageView;
        private RecyclerViewClickListener listener;

        ViewHolder(View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.coverImageView);
            itemView.setOnClickListener(this);
        }

        void setClickListener(RecyclerViewClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if(v == itemView){
                listener.onClick(v, getAdapterPosition());
            }
        }
    }
}
