package moviesmovies.com.moviesmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bilel Tabakh.
 */

public class Torrent implements Parcelable{
    private String date_uploaded;
    private String size_bytes;
    private String hash;
    private String date_uploaded_unix;
    private String peers;
    private String quality;
    private String seeds;
    private String url;
    private String size;

    private Torrent(Parcel in) {
        date_uploaded = in.readString();
        size_bytes = in.readString();
        hash = in.readString();
        date_uploaded_unix = in.readString();
        peers = in.readString();
        quality = in.readString();
        seeds = in.readString();
        url = in.readString();
        size = in.readString();
    }

    public static final Creator<Torrent> CREATOR = new Creator<Torrent>() {
        @Override
        public Torrent createFromParcel(Parcel in) {
            return new Torrent(in);
        }

        @Override
        public Torrent[] newArray(int size) {
            return new Torrent[size];
        }
    };

    public String getDateUploaded() {
        return date_uploaded;
    }

    public String getSizeBytes() {
        return size_bytes;
    }

    public String getHash () {
        return hash;
    }

    public String getDateUploadedUnix() {
        return date_uploaded_unix;
    }

    public String getPeers () {
        return peers;
    }

    public String getQuality () {
        return quality;
    }

    public String getSeeds () {
        return seeds;
    }

    public String getUrl () {
        return url;
    }

    public String getSize () {
        return size;
    }

    @Override
    public String toString() {
        return "ClassPojo [date_uploaded = "+date_uploaded+", size_bytes = "+size_bytes+", hash = "+hash+", date_uploaded_unix = "+date_uploaded_unix+", peers = "+peers+", quality = "+quality+", seeds = "+seeds+", url = "+url+", size = "+size+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_uploaded);
        dest.writeString(size_bytes);
        dest.writeString(hash);
        dest.writeString(date_uploaded_unix);
        dest.writeString(peers);
        dest.writeString(quality);
        dest.writeString(seeds);
        dest.writeString(url);
        dest.writeString(size);
    }
}
