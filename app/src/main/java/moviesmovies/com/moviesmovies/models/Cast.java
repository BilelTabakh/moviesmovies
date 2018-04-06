package moviesmovies.com.moviesmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bilel Tabakh.
 */

public class Cast implements Parcelable{

    private String character_name;
    private String name;
    private String imdb_code;
    private String url_small_image;

    protected Cast(Parcel in) {
        character_name = in.readString();
        name = in.readString();
        imdb_code = in.readString();
        url_small_image = in.readString();
    }

    public static final Creator<Cast> CREATOR = new Creator<Cast>() {
        @Override
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        @Override
        public Cast[] newArray(int size) {
            return new Cast[size];
        }
    };

    public String getCharacterName () {
        return character_name;
    }

    public String getName () {
        return name;
    }

    public String getImdbCode () {
        return imdb_code;
    }

    public String getUrlSmallImage () {
        return url_small_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [character_name = "+character_name+", name = "+name+", imdb_code = "+imdb_code+", url_small_image = "+url_small_image+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(character_name);
        dest.writeString(name);
        dest.writeString(imdb_code);
        dest.writeString(url_small_image);
    }
}
