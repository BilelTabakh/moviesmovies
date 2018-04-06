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

    public static boolean isAppRunning(final Context context, final String packageName) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        if (procInfos != null)
        {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void setSingleChoiceDialog(Context context, String[] data, final EditText editText, String title){
        final CharSequence [] items = data;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.ConfirmationDialogTheme);
        builder.setTitle(title);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText(items[which]);
            }
        });
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editText.setText("");
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.show();
            }
        });
    }

}

