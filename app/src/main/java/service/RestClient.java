package service;

import java.util.List;

import models.Dieta;
import models.Token;
import models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by HouSe on 13/08/2017.
 */

public interface RestClient {
    @FormUrlEncoded
    @POST("api-token-auth/")
    Call<Token> getToken(@Field("username") String username,
                           @Field("password") String password);

    @GET("usuario/datos/{token}/")
    Call<Usuario> getUsuario(@Path("token") String token);

    @GET("dietas/{cedula}/")
    Call<List<Dieta>> getDietas(@Path("cedula") String cedula);
}
