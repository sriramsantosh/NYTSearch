package com.aripir.nytimessearch.models;

import android.util.Log;

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

            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");
            this.publishDate = jsonObject.getString("pub_date");
            this.newsDesk = jsonObject.getString("new_desk");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia!=null && multimedia.length()>0){
                JSONObject multimediaJSON = multimedia.getJSONObject(0);
                this.thumbNail = "http://www.nytimes.com/" + multimediaJSON.getString("url") ;
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

}
