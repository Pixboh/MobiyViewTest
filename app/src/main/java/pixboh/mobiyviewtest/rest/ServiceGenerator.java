package pixboh.mobiyviewtest.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.*;
import okhttp3.Response;

import pixboh.mobiyviewtest.model.ArrayAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by pix on 8/27/17.
 */
public class ServiceGenerator {

    public static String apiBaseUrl = "http://virginia.mobyview.eu/api/";
    private static Retrofit retrofit;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(getInterceptor());
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new ArrayAdapterFactory())
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setLenient()
            .create()
            ;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(apiBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson));

    public static void changeApiBaseUrl(String newApiBaseUrl) {
        apiBaseUrl = newApiBaseUrl;

        builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(apiBaseUrl);
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public static LoggingInterceptor getInterceptor() {
        return new LoggingInterceptor();
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Logger logger = Logger.getLogger(this.getClass().getName());
            logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            logger.info(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            return response;
        }
    }

}


