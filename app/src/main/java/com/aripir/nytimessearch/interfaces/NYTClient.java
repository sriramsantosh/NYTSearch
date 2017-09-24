package com.aripir.nytimessearch.interfaces;

import com.aripir.nytimessearch.models.NYTAPIResponse;
import com.aripir.nytimessearch.util.Constants;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by saripirala on 9/24/17.
 */

public interface NYTClient {

    @GET(Constants.ARTICLE_SEARCH_URI)
    Call<NYTAPIResponse> search(@QueryMap Map<String, String> options);
}
