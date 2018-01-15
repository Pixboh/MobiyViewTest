package pixboh.mobiyviewtest.rest;

import java.util.List;

import pixboh.mobiyviewtest.model.DataItemModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by pix on 1/11/18.
 */

public interface RestService {

    @GET("entities?")
    Call<List<DataItemModel>> listData(@Query("parameters[type]") String type , @Query("page") int page, @Query("pagesize") int pagesize , @Query("sort") String sort);

    @GET("entities?parameters[type]=attraction&page=0&pagesize=20&sort=title")
    Call<List<DataItemModel>> listAttraction();

}
