package moviesmovies.com.moviesmovies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Bilel Tabakh.
 */
@Entity
public class Movie implements Parcelable {
    @PrimaryKey
    @NonNull
    private String id;

    private String title;
    private String summary;
    private String[] genres;
    private String runtime;
    private String synopsis;
    private String year;
    private String title_long;
    private String background_image_original;
    private String small_cover_image;
    private String medium_cover_image;
    private String large_cover_image;
    private String background_image;
    private String yt_trailer_code;
    private String rating;
    private String imdb_code;

    public Movie(){}

    protected Movie(Parcel in) {
        summary = in.readString();
        genres = in.createStringArray();
        medium_cover_image = in.readString();
        runtime = in.readString();
        id = in.readString();
        title = in.readString();
        synopsis = in.readString();
        year = in.readString();
        title_long = in.readString();
        background_image_original = in.readString();
        small_cover_image = in.readString();
        large_cover_image = in.readString();
        background_image = in.readString();
        yt_trailer_code = in.readString();
        rating = in.readString();
        imdb_code = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getSummary () {
        return summary;
    }

    public String getStringGenres() {
        StringBuffer strGenres;
        if(genres != null && genres.length > 0){
            strGenres = new StringBuffer();
            for (String genre: genres) {
                strGenres.append(genre);
                strGenres.append(", ");
            }
            return strGenres.substring(0, strGenres.length() - 2);
        }
        return "---";
    }

    public String getRuntime () {
        return runtime;
    }

    @NonNull
    public String getId () {
        return id;
    }

    public String getTitle () {
        return title;
    }

    public String getSynopsis () {
        return synopsis;
    }

    public String getYear () {
        return year;
    }

    public String getTitle_long () {
        return title_long;
    }

    public String getBackground_image_original () {
        return background_image_original;
    }

    public String getBackground_image () {
        return background_image;
    }

    public String getRating () {
        return rating;
    }

    public String getSmall_cover_image() {
        return small_cover_image;
    }

    public String getMedium_cover_image() {
        return medium_cover_image;
    }

    public String getLarge_cover_image() {
        return large_cover_image;
    }

    public String getYt_trailer_code() {
        return yt_trailer_code;
    }

    public String getImdb_code() {
        return imdb_code;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTitle_long(String title_long) {
        this.title_long = title_long;
    }

    public void setBackground_image_original(String background_image_original) {
        this.background_image_original = background_image_original;
    }

    public void setSmall_cover_image(String small_cover_image) {
        this.small_cover_image = small_cover_image;
    }

    public void setMedium_cover_image(String medium_cover_image) {
        this.medium_cover_image = medium_cover_image;
    }

    public void setLarge_cover_image(String large_cover_image) {
        this.large_cover_image = large_cover_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }

    public void setYt_trailer_code(String yt_trailer_code) {
        this.yt_trailer_code = yt_trailer_code;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImdb_code(String imdb_code) {
        this.imdb_code = imdb_code;
    }

    @Override
    public String toString() {
        return "ClassPojo [summary = "+summary+", genres = "+genres+", medium_cover_image = "+medium_cover_image+", runtime = "+runtime+", id = "+id+", title = "+title+", synopsis = "+synopsis+", year = "+year+", title_long = "+title_long+", background_image_original = "+background_image_original+", small_cover_image = "+small_cover_image+", large_cover_image = "+large_cover_image+", background_image = "+background_image+", yt_trailer_code = "+yt_trailer_code+", rating = "+rating+" imdb_code = "+imdb_code+" ]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(summary);
        dest.writeStringArray(genres);
        dest.writeString(medium_cover_image);
        dest.writeString(runtime);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeString(year);
        dest.writeString(title_long);
        dest.writeString(background_image_original);
        dest.writeString(small_cover_image);
        dest.writeString(large_cover_image);
        dest.writeString(background_image);
        dest.writeString(yt_trailer_code);
        dest.writeString(rating);
        dest.writeString(imdb_code);
    }
}
