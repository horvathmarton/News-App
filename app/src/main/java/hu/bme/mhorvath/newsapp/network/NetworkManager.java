package hu.bme.mhorvath.newsapp.network;

import hu.bme.mhorvath.newsapp.model.NewsData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static final String BASE_URL = "https://newsapi.org";
    private static final String APP_KEY = "ae658efa2eed44089f8bc0e027a56657";

    private static NetworkManager instance;

    private NewsApi newsApi;

    public static NetworkManager getInstance() {
        if (null == instance) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private NetworkManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsApi = retrofit.create(NewsApi.class);
    }

    public Call<NewsData> getNews(String sources) {
        return newsApi.getNews(sources, APP_KEY);
    }

}
