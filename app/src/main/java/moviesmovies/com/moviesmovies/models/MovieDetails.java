package moviesmovies.com.moviesmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Bilel Tabakh.
 */

public class MovieDetails implements Parcelable{
    private String[] genres;
    private String medium_cover_image;
    private String runtime;
    private String medium_screenshot_image1;
    private String medium_screenshot_image2;
    private String medium_screenshot_image3;
    private String download_count;
    private String id;
    private String title;
    private String date_uploaded_unix;
    private String year;
    private String title_long;
    private String background_image_original;
    private String description_intro;
    private String description_full;
    private String small_cover_image;
    private String large_cover_image;
    private String mpa_rating;
    private String large_screenshot_image1;
    private String large_screenshot_image3;
    private String large_screenshot_image2;
    private String url;
    private String date_uploaded;
    private List<Torrent> torrents;
    private String background_image;
    private List<Cast> cast;
    private String yt_trailer_code;
    private String like_count;
    private String title_english;
    private String slug;
    private String rating;
    private String imdb_code;
    private String language;

    protected MovieDetails(Parcel in) {
        genres = in.createStringArray();
        medium_cover_image = in.readString();
        runtime = in.readString();
        medium_screenshot_image1 = in.readString();
        medium_screenshot_image2 = in.readString();
        medium_screenshot_image3 = in.readString();
        download_count = in.readString();
        id = in.readString();
        title = in.readString();
        date_uploaded_unix = in.readString();
        year = in.readString();
        title_long = in.readString();
        background_image_original = in.readString();
        description_intro = in.readString();
        description_full = in.readString();
        small_cover_image = in.readString();
        large_cover_image = in.readString();
        mpa_rating = in.readString();
        large_screenshot_image1 = in.readString();
        large_screenshot_image3 = in.readString();
        large_screenshot_image2 = in.readString();
        url = in.readString();
        date_uploaded = in.readString();
        torrents = in.createTypedArrayList(Torrent.CREATOR);
        background_image = in.readString();
        cast = in.createTypedArrayList(Cast.CREATOR);
        yt_trailer_code = in.readString();
        like_count = in.readString();
        title_english = in.readString();
        slug = in.readString();
        rating = in.readString();
        imdb_code = in.readString();
        language = in.readString();
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public String getGenres () {
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

    public String getMediumCoverImage() {
        return medium_cover_image;
    }

    public String getRuntime () {
        return runtime;
    }

    public String getMediumScreenshotImage1() {
        return medium_screenshot_image1;
    }

    public String getMediumScreenshotImage2() {
        return medium_screenshot_image2;
    }

    public String getMediumScreenshotImage3() {
        return medium_screenshot_image3;
    }

    public String getDownloadCount() {
        return download_count;
    }

    public String getId () {
        return id;
    }

    public String getTitle () {
        return title;
    }

    public String getDateUploadedUnix() {
        return date_uploaded_unix;
    }

    public String getYear () {
        return year;
    }

    public String getTitleLong() {
        return title_long;
    }

    public String getBackgroundImageOriginal() {
        return background_image_original;
    }

    public String getDescriptionIntro() {
        return description_intro;
    }

    public String getDescriptionFull() {
        return description_full;
    }

    public String getSmallCoverImage() {
        return small_cover_image;
    }

    public String getLargeCoverImage() {
        return large_cover_image;
    }

    public String getMpaRating() {
        return mpa_rating;
    }

    public String getLargeScreenshotImage1() {
        return large_screenshot_image1;
    }

    public String getLargeScreenshotImage3() {
        return large_screenshot_image3;
    }

    public String getLargeScreenshotImage2() {
        return large_screenshot_image2;
    }

    public String getUrl () {
        return url;
    }

    public String getDateUploaded() {
        return date_uploaded;
    }

    public List<Torrent> getTorrents (){
        return torrents;
    }

    public String getBackgroundImage() {
        return background_image;
    }

    public List<Cast> getCast () {
        return cast;
    }

    public String getYtTrailerCode() {
        return yt_trailer_code;
    }

    public String getLikeCount() {
        return like_count;
    }

    public String getTitleEnglish() {
        return title_english;
    }

    public String getSlug () {
        return slug;
    }

    public String getRating () {
        return rating;
    }

    public String getImdbCode() {
        return imdb_code;
    }

    public String getLanguage () {
        return language;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [genres = "+genres+", medium_cover_image = "+medium_cover_image+", runtime = "+runtime+", medium_screenshot_image1 = "+medium_screenshot_image1+", medium_screenshot_image2 = "+medium_screenshot_image2+", medium_screenshot_image3 = "+medium_screenshot_image3+", download_count = "+download_count+", id = "+id+", title = "+title+", date_uploaded_unix = "+date_uploaded_unix+", year = "+year+", title_long = "+title_long+", background_image_original = "+background_image_original+", description_intro = "+description_intro+", description_full = "+description_full+", small_cover_image = "+small_cover_image+", large_cover_image = "+large_cover_image+", mpa_rating = "+mpa_rating+", large_screenshot_image1 = "+large_screenshot_image1+", large_screenshot_image3 = "+large_screenshot_image3+", large_screenshot_image2 = "+large_screenshot_image2+", url = "+url+", date_uploaded = "+date_uploaded+", torrents = "+torrents+", background_image = "+background_image+", cast = "+cast+", yt_trailer_code = "+yt_trailer_code+", like_count = "+like_count+", title_english = "+title_english+", slug = "+slug+", rating = "+rating+", imdb_code = "+imdb_code+", language = "+language+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(genres);
        dest.writeString(medium_cover_image);
        dest.writeString(runtime);
        dest.writeString(medium_screenshot_image1);
        dest.writeString(medium_screenshot_image2);
        dest.writeString(medium_screenshot_image3);
        dest.writeString(download_count);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(date_uploaded_unix);
        dest.writeString(year);
        dest.writeString(title_long);
        dest.writeString(background_image_original);
        dest.writeString(description_intro);
        dest.writeString(description_full);
        dest.writeString(small_cover_image);
        dest.writeString(large_cover_image);
        dest.writeString(mpa_rating);
        dest.writeString(large_screenshot_image1);
        dest.writeString(large_screenshot_image3);
        dest.writeString(large_screenshot_image2);
        dest.writeString(url);
        dest.writeString(date_uploaded);
        dest.writeTypedList(torrents);
        dest.writeString(background_image);
        dest.writeTypedList(cast);
        dest.writeString(yt_trailer_code);
        dest.writeString(like_count);
        dest.writeString(title_english);
        dest.writeString(slug);
        dest.writeString(rating);
        dest.writeString(imdb_code);
        dest.writeString(language);
    }
}
