package moviesmovies.com.moviesmovies.utils;

import android.content.Context;
import android.widget.Toast;

import moviesmovies.com.moviesmovies.R;

/**
 * Created by Bilel Tabakh.
 */

public class Helper {
    private Helper(){}


    public static void noInternetResponse(Context context){
        Toast.makeText(context, R.string.no_internet, Toast.LENGTH_LONG).show();
    }
}

