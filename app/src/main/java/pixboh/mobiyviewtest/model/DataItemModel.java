
package pixboh.mobiyviewtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class DataItemModel extends RealmObject implements Parcelable {
    @SerializedName("attraction_category")
    @Expose
    private AttractionCategory attractionCategory;

    @SerializedName("image")
    @Ignore
    @Expose
    private List<Image> image = new ArrayList<>();

    private RealmList<Image> image_realm = new RealmList<>();
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location_area")
    @Expose
    private LocationArea locationArea;
    @SerializedName("geo")
    @Expose
    private Geo geo;
    @SerializedName("description")
    @Ignore
    @Expose
    private List<Description> description;
    private RealmList<Description> description_realm;
    @SerializedName("opening_days")
    @Expose
    private String openingDays;
    @SerializedName("telephone")
    @Expose
    private String telephone;

    @SerializedName("price_range")
    @Expose
    private String priceRange;


    @SerializedName("type")
    @PrimaryKey
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;


    /**
     * No args constructor for use in serialization
     */
    public DataItemModel() {
    }


    protected DataItemModel(Parcel in) {
        attractionCategory = in.readParcelable(AttractionCategory.class.getClassLoader());
        address = in.readString();
        locationArea = in.readParcelable(LocationArea.class.getClassLoader());
        geo = in.readParcelable(Geo.class.getClassLoader());
        openingDays = in.readString();
        telephone = in.readString();
        priceRange = in.readString();
        type = in.readString();
        title = in.readString();
    }

    public AttractionCategory getAttractionCategory() {
        return attractionCategory;
    }

    public void setAttractionCategory(AttractionCategory attractionCategory) {
        this.attractionCategory = attractionCategory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocationArea getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(LocationArea locationArea) {
        this.locationArea = locationArea;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Description> getDescription() {
        return description;
    }

    public void setDescription(List<Description> description) {
        this.description = description;
    }

    public void setDescription(RealmList<Description> description) {
        this.description = description;
    }

    public RealmList<Image> getImage_realm() {
        return image_realm;
    }

    public void setImage_realm(RealmList<Image> image_realm) {
        this.image_realm = image_realm;
    }

    public RealmList<Description> getDescription_realm() {
        return description_realm;
    }

    public void setDescription_realm(RealmList<Description> description_realm) {
        this.description_realm = description_realm;
    }

    public String getOpeningDays() {
        return openingDays;
    }

    public void setOpeningDays(String openingDays) {
        this.openingDays = openingDays;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Creator<DataItemModel> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<DataItemModel> CREATOR = new Creator<DataItemModel>() {
        @Override
        public DataItemModel createFromParcel(Parcel in) {
            return new DataItemModel(in);
        }

        @Override
        public DataItemModel[] newArray(int size) {
            return new DataItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(attractionCategory, i);
        parcel.writeString(address);
        parcel.writeParcelable(locationArea, i);
        parcel.writeParcelable(geo, i);
        parcel.writeString(openingDays);
        parcel.writeString(telephone);
        parcel.writeString(priceRange);
        parcel.writeString(type);
        parcel.writeString(title);
    }
}
