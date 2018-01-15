
package pixboh.mobiyviewtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageLogo {

    @SerializedName("file")
    @Expose
    private File__ file;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ImageLogo() {
    }

    /**
     * 
     * @param file
     */
    public ImageLogo(File__ file) {
        super();
        this.file = file;
    }

    public File__ getFile() {
        return file;
    }

    public void setFile(File__ file) {
        this.file = file;
    }

}
