package moviesmovies.com.moviesmovies.models;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import moviesmovies.com.moviesmovies.R;

/**
 * Created by Bilel Tabakh.
 */

public class Genre {

    private String name;
    private Drawable image;
    private String filter;

    public String getName() {
        return name;
    }

    public Drawable getImage() {
        return image;
    }

    public String getFilter() {
        return filter;
    }

    private Genre(String name, Drawable image, String filter){
        this.name = name;
        this.image = image;
        this.filter = filter;
    }

    public static List<Genre> getGenres(Context context){

        List<Genre> genres = new ArrayList<>();
        Resources res = context.getResources();
        TypedArray names = res.obtainTypedArray(R.array.genres_name);
        TypedArray images = res.obtainTypedArray(R.array.genres_image);
        TypedArray filters = res.obtainTypedArray(R.array.genres_filter);

        if(names.length() == images.length() && names.length() == filters.length()){
            for(int i = 0; i < names.length(); i++){
                genres.add(new Genre(names.getString(i), images.getDrawable(i), filters.getString(i)));
            }
        }

        names.recycle();
        images.recycle();
        filters.recycle();

        return genres;
    }

}
