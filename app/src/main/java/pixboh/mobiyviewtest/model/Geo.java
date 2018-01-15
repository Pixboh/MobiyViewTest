
package pixboh.mobiyviewtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Geo extends RealmObject implements Parcelable {

    @SerializedName("latlon")
    @Expose
    private String latlon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Geo() {
    }

    /**
     * 
     * @param latlon
     */
    public Geo(String latlon) {
        super();
        this.latlon = latlon;
    }


    protected Geo(Parcel in) {
        latlon = in.readString();
    }

    public static final Creator<Geo> CREATOR = new Creator<Geo>() {
        @Override
        public Geo createFromParcel(Parcel in) {
            return new Geo(in);
        }

        @Override
        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };

    public String getLatlon() {
        return latlon;
    }

    public void setLatlon(String latlon) {
        this.latlon = latlon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(latlon);
    }
}
