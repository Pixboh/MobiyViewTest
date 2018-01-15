
package pixboh.mobiyviewtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class File extends RealmObject implements Parcelable {
    public File() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUriFull() {
        return uriFull;
    }

    public void setUriFull(String uriFull) {
        this.uriFull = uriFull;
    }

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("resource")
    @Expose
    private String resource;
    @SerializedName("uri_full")
    @Expose
    private String uriFull;

    protected File(Parcel in) {
        uri = in.readString();
        id = in.readString();
        resource = in.readString();
        uriFull = in.readString();
    }

    public static final Creator<File> CREATOR = new Creator<File>() {
        @Override
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        @Override
        public File[] newArray(int size) {
            return new File[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uri);
        parcel.writeString(id);
        parcel.writeString(resource);
        parcel.writeString(uriFull);
    }

    /**
     * No args constructor for use in serialization
     *
     */

}
