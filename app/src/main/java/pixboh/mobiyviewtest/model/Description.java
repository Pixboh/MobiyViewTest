
package pixboh.mobiyviewtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Description extends RealmObject implements Parcelable {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("summary")
    @Expose
    private String summary;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Description() {
    }

    /**
     * 
     * @param summary
     * @param value
     */
    public Description(String value, String summary) {
        super();
        this.value = value;
        this.summary = summary;
    }

    protected Description(Parcel in) {
        value = in.readString();
        summary = in.readString();
    }


    public static final Creator<Description> CREATOR = new Creator<Description>() {
        @Override
        public Description createFromParcel(Parcel in) {
            return new Description(in);
        }

        @Override
        public Description[] newArray(int size) {
            return new Description[size];
        }
    };

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(value);
        parcel.writeString(summary);
    }
}
