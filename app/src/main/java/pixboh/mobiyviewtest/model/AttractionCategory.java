
package pixboh.mobiyviewtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class AttractionCategory extends RealmObject implements Parcelable {

    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("vid")
    @Expose
    private String vid;
    @SerializedName("name")
    @Expose
    private String name;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public AttractionCategory() {
    }

    public AttractionCategory(String tid, String vid, String name) {
        this.tid = tid;
        this.vid = vid;
        this.name = name;
    }

    protected AttractionCategory(Parcel in) {
        tid = in.readString();
        vid = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tid);
        dest.writeString(vid);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttractionCategory> CREATOR = new Creator<AttractionCategory>() {
        @Override
        public AttractionCategory createFromParcel(Parcel in) {
            return new AttractionCategory(in);
        }

        @Override
        public AttractionCategory[] newArray(int size) {
            return new AttractionCategory[size];
        }
    };
}
