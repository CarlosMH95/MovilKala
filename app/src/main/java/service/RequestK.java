package service;

import android.text.TextUtils;
import android.util.Log;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HouSe on 15/08/2017.
 */

public class RequestK {
    private static final String TAG = "RequestK";
    private static final String BASE_URL = "http://192.168.0.3:8000/api/";
    private static Retrofit.Builder builder = null;
    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static void init(){
        if(builder == null) {
            builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
        }

        /*if (retrofit == null) {
            retrofit = builder.build();
            return retrofit;
        }*/
    }

    public static <S> S createService(Class<S> serviceClass) {
        Log.e(TAG, "OK 1.1");
        return createService(serviceClass, null, null);
    }

    public static <S> S createService( Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            Log.e(TAG, "authToken: "+authToken);
            return createService(serviceClass, authToken);
        }
        Log.e(TAG, "OK 1.2");
        System.out.println("XYZ: "+password);
        return null; //createService(serviceClass, null, null);
    }

    public static <S> S createService( Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }
        Log.e(TAG, "OK 1.3");
        return retrofit.create(serviceClass);
    }
}
