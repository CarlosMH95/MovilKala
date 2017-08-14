package service;

import models.PokemonFeed;
import models.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by HouSe on 13/08/2017.
 */

public interface RestClient {
    @POST("autenticar")
    Call<Result> autenticar(@Query("username") String username, @Query("password") String password);
}
