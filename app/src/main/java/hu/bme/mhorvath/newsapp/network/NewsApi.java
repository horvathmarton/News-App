package hu.bme.mhorvath.newsapp.network;

import hu.bme.mhorvath.newsapp.model.NewsData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("/top-headlines")
    Call<NewsData> getNews(@Query("sources") String sources, @Query("apiKey")String apiKey);

}
