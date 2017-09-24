package com.aripir.nytimessearch.util;

/**
 * Created by saripirala on 9/24/17.
 */

public class Constants {

    public static final String BASE_URL = "http://api.nytimes.com";
    public static final String ARTICLE_SEARCH_URI = "/svc/search/v2/articlesearch.json";

    public static final String API_KEY = "cf6f54a4f8ad42e6899acaa526428ca8";
    public static final String IMAGE_PREFIX_URL = "http://www.nytimes.com/";

    public static class Article{
        public static final String WEB_URL = "web_url";
        public static final String HEADLINE = "headline";
        public static final String PUB_DATE = "pub_date";
        public static final String NEWS_DESK = "new_desk";
        public static final String MAIN = "main";
        public static final String MULTIMEDIA = "multimedia";
        public static final String WIDE = "wide";
        public static final String URL = "url";
        public static final String SUBTYPE = "subtype";
    }

    public static class Filter{

    }
}
