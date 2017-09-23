package com.aripir.nytimessearch.network;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by saripirala on 9/22/17.
 */

public class ArticleApi {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://api.nytimes.com/svc/search/v2/articlesearch.json/")
                    .build();
        }
        return retrofit;
    }

}
