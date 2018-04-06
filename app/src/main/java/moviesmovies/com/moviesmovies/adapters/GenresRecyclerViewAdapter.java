package moviesmovies.com.moviesmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.activities.FragmentHolderActivity;
import moviesmovies.com.moviesmovies.interfaces.RecyclerViewClickListener;
import moviesmovies.com.moviesmovies.models.Genre;

/**
 * Created by Bilel Tabakh.
 */

public class GenresRecyclerViewAdapter extends RecyclerView.Adapter<GenresRecyclerViewAdapter.ViewHolder> {

    private List<Genre> genreList;
    private Context context;

    public GenresRecyclerViewAdapter(Context context, List<Genre> genreList){
        this.context = context;
        this.genreList = genreList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_genres, parent, false);
        return new GenresRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Genre genre = genreList.get(position);
        holder.backgroundImageView.setImageDrawable(genre.getImage());
        holder.nameTextView.setText(genre.getName());

        holder.setClickListerner(new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(context, FragmentHolderActivity.class);
                i.putExtra("genre", genre.getName());
                i.putExtra("display", "genre");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView backgroundImageView;
        TextView nameTextView;

        private RecyclerViewClickListener listener;

        ViewHolder(View itemView) {
            super(itemView);
            backgroundImageView = itemView.findViewById(R.id.backgroundImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            itemView.setOnClickListener(this);
        }

        void setClickListerner(RecyclerViewClickListener listerner){ this.listener = listerner; }

        @Override
        public void onClick(View v) {
            if(v == itemView){
                listener.onClick(v, getAdapterPosition());
            }
        }
    }

}
