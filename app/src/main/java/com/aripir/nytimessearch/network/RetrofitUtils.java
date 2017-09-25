package com.aripir.nytimessearch.network;

import android.util.Log;

import com.aripir.nytimessearch.util.Constants;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        OkHttpClient client = new OkHttpClient();

        return new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor())
                .addInterceptor(retryInterceptor())
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

    private static Interceptor retryInterceptor(){
        return chain -> {
            Request request = chain.request();

            Response response = chain.proceed(request);

            int tryCount = 0;
            while (!response.isSuccessful() && tryCount < 3) {

                Log.d("intercept", "Request is not successful - " + tryCount);

                tryCount++;

                // retry the request
                response = chain.proceed(request);
            }

            // otherwise just pass the original response on
            return response;
        };
    }

}
