package com.aripir.nytimessearch.network;

import com.aripir.nytimessearch.util.Constants;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saripirala on 9/24/17.
 */

public class RetrofitUtils {

    public static Retrofit getArticle() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient client() {
        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor())
                .build();
    }

    private static Interceptor apiKeyInterceptor() {
        return chain -> {
            Request request = chain.request();
            HttpUrl url = request.url()
                    .newBuilder()
                    .addQueryParameter("api_key", Constants.API_KEY)
                    .build();
            request = request.newBuilder()
                    .url(url)
                    .build();
            return chain.proceed(request);
        };
    }

}
