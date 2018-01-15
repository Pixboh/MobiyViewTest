
package pixboh.mobiyviewtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscountImage {

    @SerializedName("file")
    @Expose
    private File_ file;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DiscountImage() {
    }

    /**
     * 
     * @param file
     */
    public DiscountImage(File_ file) {
        super();
        this.file = file;
    }

    public File_ getFile() {
        return file;
    }

    public void setFile(File_ file) {
        this.file = file;
    }

}
