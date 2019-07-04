package kr.koohyongmo.newsapp.retrofit;

import kr.koohyongmo.newsapp.data.NewsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NewsApiRepo {
    public static final String baseURL = "https://newsapi.org/";

    @GET("v2/everything/")
    Call<NewsResponse> getNewsResponse(
            @Query("q") String query,
            @Query("apiKey") String apiKey
    );
}
