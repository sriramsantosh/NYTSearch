package com.aripir.nytimessearch.activities;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aripir.nytimessearch.adapters.ArticleArrayAdapter;
import com.aripir.nytimessearch.listeners.EndlessRecyclerViewScrollListener;
import com.aripir.nytimessearch.models.Article;
import com.aripir.nytimessearch.R;
import com.aripir.nytimessearch.fragments.FilterFragment;
import com.aripir.nytimessearch.models.Doc;
import com.aripir.nytimessearch.models.FilterPreferences;
import com.aripir.nytimessearch.database.UserPreferencesDBHelper;
import com.aripir.nytimessearch.interfaces.NYTClient;
import com.aripir.nytimessearch.models.NYTAPIResponse;
import com.aripir.nytimessearch.network.RetrofitUtils;
import com.aripir.nytimessearch.util.CommonLib;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements FilterFragment.OnCompleteListener {

    private List<Article> articles;
    private ArticleArrayAdapter articleArrayAdapter;
    private MenuItem searchItem;
    private UserPreferencesDBHelper userPreferencesDBHelper;
    private EndlessRecyclerViewScrollListener scrollListener;
    private static String searchQuery;
    private NYTClient client;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @BindView(R.id.pbLoadMore)
    ProgressBar pbLoadMore;

    @BindView(R.id.gvArticles)
    RecyclerView recyclerView;

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        pbLoading.setVisibility(View.VISIBLE);

        checkForInternetAndNavigate();

        userPreferencesDBHelper = UserPreferencesDBHelper.getInstance(getApplicationContext());
        if (userPreferencesDBHelper.isDBEmpty())
            userPreferencesDBHelper.insertFilterPreferences(new FilterPreferences()); // Enter default preferences into DB

        setUpViews();
    }


    private void setUpViews() {
       // recyclerView = (RecyclerView) findViewById(R.id.gvArticles);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        articles = new ArrayList<>();
        articleArrayAdapter = new ArticleArrayAdapter(this, articles);
        recyclerView.setAdapter(articleArrayAdapter);
        searchQuery = "";

        client = RetrofitUtils.getArticle().create(NYTClient.class);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                pbLoadMore.setVisibility(View.VISIBLE);
                searchAndDisplayResultsWithFilters(userPreferencesDBHelper.getUserPreferences(), page, searchQuery);
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        searchAndDisplayResultsWithFilters(userPreferencesDBHelper.getUserPreferences(), 0, searchQuery);
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
                articles.clear(); // Clear everything and only display with query and filters.
                searchQuery = query;
                pbLoading.setVisibility(View.VISIBLE);
                searchAndDisplayResultsWithFilters(userPreferencesDBHelper.getUserPreferences(), 0, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        if (id == R.id.action_filter) {
            showFilterDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFilterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment filterFragment = FilterFragment.newInstance();
        filterFragment.show(fm, "Filter");
    }


    @Override
    public void onComplete(FilterPreferences filterPreferences) {

        articles.clear();
        pbLoading.setVisibility(View.VISIBLE);
        searchAndDisplayResultsWithFilters(filterPreferences, 0, searchQuery);
    }


    private void searchAndDisplayResultsWithFilters(FilterPreferences filterPreferences, int page, String searchQuery) {
        checkForInternetAndNavigate();

        Call<NYTAPIResponse> responseCall = client.search(CommonLib.getSearchOptions(filterPreferences, searchQuery, page));

        responseCall.enqueue(new Callback<NYTAPIResponse>() {
            @Override
            public void onResponse(Call<NYTAPIResponse> call, Response<NYTAPIResponse> response) {

                NYTAPIResponse rsp = response.body();

                if(rsp !=null){

                    com.aripir.nytimessearch.models.Response response1 = rsp.getResponse();
                    List<Doc> docs = response1.getDocs();

                    try {
                        JSONArray jsonArray = new JSONArray(docs.toString());
                        List<Article> articlesLatest= Article.fromJSONArray(jsonArray);
                        articles.addAll( articlesLatest);
                        pbLoading.setVisibility(View.GONE);
                        pbLoadMore.setVisibility(View.GONE);
                        recyclerView.swapAdapter(new ArticleArrayAdapter(getApplicationContext(), articles), false);

                    } catch (JSONException e) {
                        Log.d("DEBUG", "Exception caught" + e.getLocalizedMessage());
                    }
                }else{
                    Log.d("DEBUG", "Response body is null");
                    pbLoading.setVisibility(View.GONE);
                    pbLoadMore.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NYTAPIResponse> call, Throwable t) {

                Log.d("DEBUG", t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong while fetching data :(, app will try to retry couple of times", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkForInternetAndNavigate() {
        if( !isNetworkAvailable() || !CommonLib.isOnline()){
            navitageToNoInternetActivity();
        }
    }

    private void navitageToNoInternetActivity(){
        Intent intent = new Intent(this, NoInternetConnectionActivity.class);
        intent.putExtra("activityName", "ArticleActivity");
        startActivity(intent);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


}
