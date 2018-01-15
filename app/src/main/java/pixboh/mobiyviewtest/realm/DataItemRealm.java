
package pixboh.mobiyviewtest.realm;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class DataItemRealm extends RealmObject implements Parcelable {
    private String attractionCategory;

    private String image;
    private String address;
    private String locationArea;
    private String latlong;
    private String description;
    private String openingDays;
    private String telephone;
    private String price;
    private String type;
    private String attraction_type;
    private String title;

    public DataItemRealm() {
    }

    protected DataItemRealm(Parcel in) {
        attractionCategory = in.readString();
        image = in.readString();
        address = in.readString();
        locationArea = in.readString();
        latlong = in.readString();
        description = in.readString();
        openingDays = in.readString();
        telephone = in.readString();
        price = in.readString();
        type = in.readString();
        attraction_type = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attractionCategory);
        dest.writeString(image);
        dest.writeString(address);
        dest.writeString(locationArea);
        dest.writeString(latlong);
        dest.writeString(description);
        dest.writeString(openingDays);
        dest.writeString(telephone);
        dest.writeString(price);
        dest.writeString(type);
        dest.writeString(attraction_type);
        dest.writeString(title);
    }

    public DataItemRealm(String attractionCategory, String image, String address, String locationArea, String latlong, String description, String openingDays, String telephone, String price, String type, String attraction_type, String title) {
        this.attractionCategory = attractionCategory;
        this.image = image;
        this.address = address;
        this.locationArea = locationArea;
        this.latlong = latlong;
        this.description = description;
        this.openingDays = openingDays;
        this.telephone = telephone;
        this.price = price;
        this.type = type;
        this.attraction_type = attraction_type;
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttraction_type() {
        return attraction_type;
    }

    public void setAttraction_type(String attraction_type) {
        this.attraction_type = attraction_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public static final Creator<DataItemRealm> CREATOR = new Creator<DataItemRealm>() {
        @Override
        public DataItemRealm createFromParcel(Parcel in) {
            return new DataItemRealm(in);
        }

        @Override
        public DataItemRealm[] newArray(int size) {
            return new DataItemRealm[size];
        }
    };

    public String getAttractionCategory() {
        return attractionCategory;
    }

    public void setAttractionCategory(String attractionCategory) {
        this.attractionCategory = attractionCategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


}
