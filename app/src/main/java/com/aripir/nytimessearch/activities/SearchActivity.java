package com.aripir.nytimessearch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aripir.nytimessearch.adapters.ArticleArrayAdapter;
import com.aripir.nytimessearch.listeners.EndlessRecyclerViewScrollListener;
import com.aripir.nytimessearch.models.Article;
import com.aripir.nytimessearch.R;
import com.aripir.nytimessearch.fragments.FilterFragment;
import com.aripir.nytimessearch.models.FilterPreferences;
import com.aripir.nytimessearch.models.UserPreferencesDBHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements FilterFragment.OnCompleteListener{

    Toolbar toolbar;
    private List<Article> articles;
    private ArticleArrayAdapter articleArrayAdapter;
    private RecyclerView recyclerView;
    private MenuItem searchItem;
    private UserPreferencesDBHelper userPreferencesDBHelper;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        userPreferencesDBHelper = UserPreferencesDBHelper.getInstance(getApplicationContext());
        if(userPreferencesDBHelper.isDBEmpty())
            userPreferencesDBHelper.insertFilterPreferences(new FilterPreferences()); // Enter default preferences into DB

        setUpViews();
    }


    private void setUpViews()
    {
        recyclerView = (RecyclerView) findViewById(R.id.gvArticles);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        articles = new ArrayList<>();
        articleArrayAdapter = new ArticleArrayAdapter(this, articles);
        recyclerView.setAdapter(articleArrayAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
               // loadNextDataFromApi(page);

                searchAndDisplayResults(page, "");
            }
        };

        recyclerView.addOnScrollListener(scrollListener);


        searchAndDisplayResults(0, "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        searchItem = menu.findItem(R.id.action_search);


        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();
                //TODO showProgressBar(); // Have a beautiful method here.... https://github.com/jpardogo/GoogleProgressBar
                articles.clear();
                searchAndDisplayResults(0, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    private void searchAndDisplayResults(int page, String query)
    {
        FilterPreferences filterPreferences = userPreferencesDBHelper.getUserPreferences();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";


        String[] date= filterPreferences.getBeginDate().split("/");

        StringBuilder sb = new StringBuilder();

        if(filterPreferences.isArts() || filterPreferences.isFashion() || filterPreferences.isSports())
            sb.append("news_desk:(");

        if(filterPreferences.isArts())
            sb.append("'Arts' ");
        if(filterPreferences.isFashion())
            sb.append("'Fashion' ");
        if(filterPreferences.isSports())
            sb.append("'Sports'");

        sb.trimToSize();

        RequestParams requestParams = new RequestParams();
        requestParams.put("api-key", "cf6f54a4f8ad42e6899acaa526428ca8");
        requestParams.put("page", page);
        requestParams.put("sort", filterPreferences.getSort().toLowerCase());
        if(query!=null && !query.isEmpty())
        requestParams.put("q", query);
        if(date[0].length()==1)
            requestParams.put("begin_date", date[2]+"0"+ date[0]+date[1]);
        else
            requestParams.put("begin_date", date[2]+"0"+ date[0]+date[1]);
        if(filterPreferences.isArts() || filterPreferences.isFashion() || filterPreferences.isSports()){
            sb.append(")");
            requestParams.put("fq", sb.toString());
        }


        client.get(url, requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleResults = null;
                try{
                    articleResults =  response.getJSONObject("response").getJSONArray("docs");


                    articles.addAll(Article.fromJSONArray(articleResults));
                    recyclerView.swapAdapter(new ArticleArrayAdapter(getApplicationContext(), articles), false);
                }catch(JSONException e){

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //Toast.makeText(getApplicationContext(),"Search", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_filter) {
            showFilterDialog();
            //Toast.makeText(getApplicationContext(),"Filter", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance();
        filterFragment.show(fm, "fragment_edit_name");
    }


    @Override
    public void onComplete(String datePick, String timeFrame, boolean arts, boolean fashion, boolean sports) {

        //Insert it into DB
        userPreferencesDBHelper.insertFilterPreferences(datePick,timeFrame, arts, fashion, sports);

        searchAndDisplayResultsWithFilters(datePick, timeFrame, arts, fashion, sports);


        System.out.println(timeFrame);
        System.out.println(datePick);
        System.out.println(arts);
        System.out.println(fashion);
        System.out.println(sports);
    }

    private void searchAndDisplayResultsWithFilters(String datePick, String timeFrame, boolean arts, boolean fashion, boolean sports) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        String[] date= datePick.split("/");

        StringBuilder sb = new StringBuilder();

        if(arts || fashion || sports)
            sb.append("news_desk:(");

        if(arts)
            sb.append("'Arts' ");
        if(fashion)
            sb.append("'Fashion' ");
        if(sports)
            sb.append("'Sports'");

        sb.trimToSize();


        RequestParams requestParams = new RequestParams();
        requestParams.put("api-key", "cf6f54a4f8ad42e6899acaa526428ca8");
        requestParams.put("page", 0);
        requestParams.put("sort", timeFrame.toLowerCase());
        if(date[0].length()==1)
            requestParams.put("begin_date", date[2]+"0"+ date[0]+date[1]);
        else
            requestParams.put("begin_date", date[2]+"0"+ date[0]+date[1]);
        if(arts || fashion || sports) {
            sb.append(")");
            requestParams.put("fq", sb.toString());
        }

        client.get(url, requestParams, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleResults = null;
                try{
                    articleResults =  response.getJSONObject("response").getJSONArray("docs");

                    if(articles.size()>0)
                        articles.clear();
                    articles.addAll(Article.fromJSONArray(articleResults));
                    recyclerView.swapAdapter(new ArticleArrayAdapter(getApplicationContext(), articles), false);
                }catch(JSONException e){

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "Status code: " + statusCode);
                Log.d("DEBUG", errorResponse.toString());
            }
        });



    }


}
