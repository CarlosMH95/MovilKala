package service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HouSe on 13/08/2017.
 */

public class AuthenticationInterceptor implements Interceptor {

    private String auth;
    private boolean withCredentials;

    public AuthenticationInterceptor(String auth, boolean withCredentials) {
        this.auth = auth;
        this.withCredentials = withCredentials;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String type = (withCredentials) ? "" : "Token ";
        Request.Builder builder = original.newBuilder().header("Authorization", type + auth);
        Request request = builder.build();
        return chain.proceed(request);
    }
}