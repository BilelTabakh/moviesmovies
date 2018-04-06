package moviesmovies.com.moviesmovies.models;

/**
 * Created by Bilel Tabakh.
 */

public class MoviesResponse {
    private String status;
    private String status_message;
    private Data data;

    public String getStatus () {
        return status;
    }

    public String getStatusMessage() {
        return status_message;
    }

    public Data getData () {
        return data;
    }

    @Override
    public String toString() {
        return "ClassPojo [status = "+status+", status_message = "+status_message+", data = "+data+"]";
    }
}
