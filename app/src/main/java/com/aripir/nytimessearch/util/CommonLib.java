package com.aripir.nytimessearch.util;

import com.aripir.nytimessearch.models.FilterPreferences;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by saripirala on 9/24/17.
 */

public class CommonLib {

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Map<String, String> getSearchOptions(FilterPreferences filterPreferences, String searchQuery, int page){

        Map<String, String> searchOptions = new HashMap<>();

        String[] date = filterPreferences.getBeginDate().split("/");

        StringBuilder sb = new StringBuilder();

        if (filterPreferences.isArts() || filterPreferences.isFashion() || filterPreferences.isSports())
            sb.append("news_desk:(");

        if (filterPreferences.isArts())
            sb.append("'Arts' ");
        if (filterPreferences.isFashion())
            sb.append("'Fashion' ");
        if (filterPreferences.isSports())
            sb.append("'Sports'");

        sb.trimToSize();

        searchOptions.put("page", Integer.toString(page));
        searchOptions.put("sort", filterPreferences.getSort().toLowerCase());
        if (date[0].length() == 1)
            searchOptions.put("begin_date", date[2] + "0" + date[0] + date[1]);
        else
            searchOptions.put("begin_date", date[2] + date[0] + date[1]);
        if (filterPreferences.isArts() || filterPreferences.isFashion() || filterPreferences.isSports()) {
            sb.append(")");
            searchOptions.put("fq", sb.toString());
        }
        if (searchQuery != null && !searchQuery.isEmpty())
            searchOptions.put("q", searchQuery);

        return searchOptions;
    }
}
