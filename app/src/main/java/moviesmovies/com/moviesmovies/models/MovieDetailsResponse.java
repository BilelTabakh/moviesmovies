package moviesmovies.com.moviesmovies.models;

/**
 * Created by Bilel Tabakh.
 */

public class MovieDetailsResponse {
    private String status;
    private String status_message;
    private DataDetails data;

    public String getStatus () {
        return status;
    }

    public String getStatusMessage() {
        return status_message;
    }

    public DataDetails getDataDetails () {
        return data;
    }

    @Override
    public String toString() {
        return "ClassPojo [status = "+status+", status_message = "+status_message+", data = "+data+"]";
    }
}
