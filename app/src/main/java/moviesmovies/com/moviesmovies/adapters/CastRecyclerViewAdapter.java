package moviesmovies.com.moviesmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import moviesmovies.com.moviesmovies.models.Cast;

/**
 * Created by Bilel Tabakh.
 */

public class CastRecyclerViewAdapter extends RecyclerView.Adapter<CastRecyclerViewAdapter.ViewHolder> {

    private List<Cast> castList;
    private Context context;

    public CastRecyclerViewAdapter (Context context, List<Cast> castList){
        this.context = context;
        this.castList = castList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_cast, parent, false);
        return new CastRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Cast cast = castList.get(position);

        holder.actorNameTextView.setText(cast.getName());
        holder.actorCharacterTextView.setText(context.getString(R.string.character, cast.getCharacterName()));
        holder.actorImdbImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(context.getString(R.string.imdb_actor, cast.getImdbCode())));
                context.startActivity(i);
            }
        });

        Glide.with(context)
                .load(cast.getUrlSmallImage())
                .placeholder(R.drawable.actor_placeholder)
                .into(holder.actorImageView);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView actorImageView;
        private TextView actorNameTextView;
        private TextView actorCharacterTextView;
        private ImageButton actorImdbImageButton;

        ViewHolder(View itemView) {
            super(itemView);

            actorImageView = itemView.findViewById(R.id.actorImageView);
            actorNameTextView = itemView.findViewById(R.id.actorNameTextView);
            actorCharacterTextView = itemView.findViewById(R.id.actorCharacterTextView);
            actorImdbImageButton = itemView.findViewById(R.id.actorImdbImageButton);
        }
    }
}
