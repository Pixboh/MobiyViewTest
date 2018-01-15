
package pixboh.mobiyviewtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Und {

    @SerializedName("value")
    @Expose
    private String value;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Und() {
    }

    /**
     * 
     * @param value
     */
    public Und(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
