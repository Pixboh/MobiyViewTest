package pixboh.mobiyviewtest.rest;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pix on 8/27/17.
 */
public interface ApiService {

    @GET("testubuntu.php")
    Call<String> getMessage();
}
