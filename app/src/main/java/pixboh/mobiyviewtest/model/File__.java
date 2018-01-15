
package pixboh.mobiyviewtest.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File__ {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("resource")
    @Expose
    private String resource;
    @SerializedName("uri_full")
    @Expose
    private String uriFull;
    @SerializedName("previews")
    @Expose
    private List<Object> previews = new ArrayList<Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public File__() {
    }

    /**
     *
     * @param id
     * @param previews
     * @param uriFull
     * @param resource
     * @param uri
     */
    public File__(String uri, String id, String resource, String uriFull, List<Object> previews) {
        super();
        this.uri = uri;
        this.id = id;
        this.resource = resource;
        this.uriFull = uriFull;
        this.previews = previews;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUriFull() {
        return uriFull;
    }

    public void setUriFull(String uriFull) {
        this.uriFull = uriFull;
    }

    public List<Object> getPreviews() {
        return previews;
    }

    public void setPreviews(List<Object> previews) {
        this.previews = previews;
    }

}
