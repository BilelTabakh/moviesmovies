package moviesmovies.com.moviesmovies.activities;

import android.os.Bundle;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.utils.YouTubeConfig;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private String strTrailer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        // Setting the activity size to match parent
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        strTrailer = getIntent().getStringExtra("trailer");
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(strTrailer);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {}
        };
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        youTubePlayerView.initialize(YouTubeConfig.getApiKey(), onInitializedListener);
    }
}
