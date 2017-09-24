package com.aripir.nytimessearch.models;

import android.util.Log;

import com.aripir.nytimessearch.util.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saripirala on 9/18/17.
 */

@Parcel
public class Article {

    private String webUrl;
    private String headline;
    private String thumbNail;
    private String publishDate;
    private String newsDesk;

    public Article(){

    }

    public Article(JSONObject jsonObject)
    {
        try{

            this.webUrl = jsonObject.getString(Constants.Article.WEB_URL);
            this.headline = jsonObject.getJSONObject(Constants.Article.HEADLINE).getString(Constants.Article.MAIN);
            this.publishDate = jsonObject.getString(Constants.Article.PUB_DATE);
            this.newsDesk = jsonObject.getString(Constants.Article.NEWS_DESK);
            JSONArray multimedia = jsonObject.getJSONArray(Constants.Article.MULTIMEDIA);
            if(multimedia!=null && multimedia.length()>0){
                for(int i=0;i<multimedia.length(); i++){
                    JSONObject multimediaJSON = multimedia.getJSONObject(i);
                    if(multimediaJSON.has(Constants.Article.SUBTYPE))
                        if(multimediaJSON.getString(Constants.Article.SUBTYPE).equalsIgnoreCase(Constants.Article.WIDE))
                            this.thumbNail = Constants.IMAGE_PREFIX_URL + multimediaJSON.getString(Constants.Article.URL);

                }
            //    JSONObject multimediaJSON = multimedia.getJSONObject(0);
            //    this.thumbNail = "http://www.nytimes.com/" + multimediaJSON.getString("url") ;
            }else{
                this.thumbNail = "";
            }

        }catch (JSONException e){
            Log.d("DEBUG", "exception occurred while parsing json");
        }
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public String getPublishDate(){return publishDate;}

    public String getNewsDesk() { return newsDesk; }


    public static List<Article> fromJSONArray(JSONArray array){

        List<Article> results = new ArrayList<>();

        for(int i =0; i<array.length(); i++){
            try{
                results.add(new Article(array.getJSONObject(i)));
            }catch (JSONException e){

            }
        }
        return results;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
