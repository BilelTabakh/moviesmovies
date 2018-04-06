package moviesmovies.com.moviesmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

import moviesmovies.com.moviesmovies.R;

public class SplashScreenActivity extends AppCompatActivity {

    private KenBurnsView backgroundImage;
    private ImageView imageView;
    private Animation logoAnimation;

    private final int SPLASH_DURATION = 5000;
    private final int KENBURNS_DURATION = 6000;
    private final int LOGO_ANIMATION_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        imageView = (ImageView) findViewById(R.id.imageView);

        backgroundImage = (KenBurnsView) findViewById(R.id.backgroundImage);
        RandomTransitionGenerator generator = new RandomTransitionGenerator(KENBURNS_DURATION, new AccelerateDecelerateInterpolator());
        backgroundImage.setTransitionGenerator(generator);

        startLogoAnimation(LOGO_ANIMATION_DELAY);
        navigateToNextScreen(this, SPLASH_DURATION, MainActivity.class);
    }

    private void navigateToNextScreen(final Context context, int delayInMs, final Class destinationClass){
        // Wait delayInMs seconds and go to the app
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, destinationClass));
                backgroundImage.pause();
                finish();
            }
        }, delayInMs);
    }

    private void startLogoAnimation(int delayInMs){
        // Wait delayInMs seconds and start the animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(logoAnimation);
            }
        }, delayInMs);
    }
}
