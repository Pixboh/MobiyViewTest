
package pixboh.mobiyviewtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class LocationArea extends RealmObject implements Parcelable{

    @SerializedName("tid")
    @Expose
    private String tid;
    @SerializedName("vid")
    @Expose
    private String vid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("vocabulary_machine_name")
    @Expose
    private String vocabularyMachineName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LocationArea() {
    }

    /**
     * 
     * @param weight
     * @param description
     * @param name
     * @param format
     * @param tid
     * @param vocabularyMachineName
     * @param vid
     */
    public LocationArea(String tid, String vid, String name, String description, String format, String weight, String vocabularyMachineName) {
        super();
        this.tid = tid;
        this.vid = vid;
        this.name = name;
        this.description = description;
        this.format = format;
        this.weight = weight;
        this.vocabularyMachineName = vocabularyMachineName;
    }

    protected LocationArea(Parcel in) {
        tid = in.readString();
        vid = in.readString();
        name = in.readString();
        description = in.readString();
        format = in.readString();
        weight = in.readString();
        vocabularyMachineName = in.readString();
    }

    public static final Creator<LocationArea> CREATOR = new Creator<LocationArea>() {
        @Override
        public LocationArea createFromParcel(Parcel in) {
            return new LocationArea(in);
        }

        @Override
        public LocationArea[] newArray(int size) {
            return new LocationArea[size];
        }
    };

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVocabularyMachineName() {
        return vocabularyMachineName;
    }

    public void setVocabularyMachineName(String vocabularyMachineName) {
        this.vocabularyMachineName = vocabularyMachineName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tid);
        parcel.writeString(vid);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(format);
        parcel.writeString(weight);
        parcel.writeString(vocabularyMachineName);
    }
}
