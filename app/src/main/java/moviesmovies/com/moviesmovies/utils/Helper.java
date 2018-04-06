package moviesmovies.com.moviesmovies.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

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

