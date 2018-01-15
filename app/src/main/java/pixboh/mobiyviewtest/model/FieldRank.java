
package pixboh.mobiyviewtest.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FieldRank {

    @SerializedName("und")
    @Expose
    private List<Und> und = new ArrayList<Und>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public FieldRank() {
    }

    /**
     * 
     * @param und
     */
    public FieldRank(List<Und> und) {
        super();
        this.und = und;
    }

    public List<Und> getUnd() {
        return und;
    }

    public void setUnd(List<Und> und) {
        this.und = und;
    }

}
