package moviesmovies.com.moviesmovies.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import moviesmovies.com.moviesmovies.R;
import moviesmovies.com.moviesmovies.adapters.ScreenshotViewPagerAdapter;

public class ScreenshotActivity extends AppCompatActivity {

    private ViewPager screenshotViewpager;
    private int positionClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        screenshotViewpager = (ViewPager) findViewById(R.id.screenshotViewpager);
        Intent intent = getIntent();
        positionClicked = intent.getIntExtra("position", 0);
        ScreenshotViewPagerAdapter adapter = new ScreenshotViewPagerAdapter(this, intent.getStringArrayExtra("largeScreenshots"));
        screenshotViewpager.setAdapter(adapter);
        screenshotViewpager.setCurrentItem(positionClicked);
    }
}
