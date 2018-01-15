
package pixboh.mobiyviewtest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestCategory {

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
    public RestCategory() {
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
    public RestCategory(String tid, String vid, String name, String description, String format, String weight, String vocabularyMachineName) {
        super();
        this.tid = tid;
        this.vid = vid;
        this.name = name;
        this.description = description;
        this.format = format;
        this.weight = weight;
        this.vocabularyMachineName = vocabularyMachineName;
    }

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

}
