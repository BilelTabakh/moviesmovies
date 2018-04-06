package moviesmovies.com.moviesmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.activities.ScreenshotActivity;
import moviesmovies.com.moviesmovies.interfaces.RecyclerViewClickListener;

/**
 * Created by Bilel Tabakh.
 */

public class ScreenshotsRecyclerViewAdapter extends RecyclerView.Adapter<ScreenshotsRecyclerViewAdapter.ViewHolder> {

    private String[] mediumScreenshots;
    private String[] largeScreenshots;
    private Context context;

    public ScreenshotsRecyclerViewAdapter(Context context, String[] mediumScreenshots, String[] largeScreenshots){
        this.context = context;
        this.mediumScreenshots = mediumScreenshots;
        this.largeScreenshots = largeScreenshots;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_screenshots, parent, false);
        return new ScreenshotsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context)
                .load(mediumScreenshots[position])
                .placeholder(R.drawable.screenshot_placeholder)
                .into(holder.screenshotImageView);

        if (mediumScreenshots.length == largeScreenshots.length){
            holder.setClickListener(new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent intent = new Intent(context, ScreenshotActivity.class);
                    intent.putExtra("largeScreenshots", largeScreenshots);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mediumScreenshots.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView screenshotImageView;
        private RecyclerViewClickListener listener;

        ViewHolder(View itemView) {
            super(itemView);
            screenshotImageView = (ImageView) itemView.findViewById(R.id.screenshotImageView);
            itemView.setOnClickListener(this);
        }

        void setClickListener (RecyclerViewClickListener listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
