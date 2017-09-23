package com.aripir.nytimessearch.models;

import java.util.Calendar;

/**
 * Created by saripirala on 9/21/17.
 */

public class FilterPreferences {

    private String beginDate;
    private String sort;
    private boolean arts;
    private boolean fashion;
    private boolean sports;

    public static final String OLDEST = "Oldest";
    public static final String NEWEST = "Newest";
    public static final String ARTS = "arts";
    public static final String FASHION = "fashion";
    public static final String SPORTS = "sports";

    public FilterPreferences(){

        Calendar mCurrentDate = Calendar.getInstance();

        int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        int month = mCurrentDate.get(Calendar.MONTH);
        int year = mCurrentDate.get(Calendar.YEAR);

        setBeginDate(month+"/" + day + "/" + year);
        setSort("Newest");
        setArts(true);
        setSports(true);
        setFashion(true);
    }

    public FilterPreferences(String beginDate, String sort, boolean isArts, boolean isFashion, boolean isSports){
        this.beginDate = beginDate;
        this.sort = sort;
        this.arts = isArts;
        this.fashion = isFashion;
        this.sports = isSports;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isArts() {
        return arts;
    }

    public void setArts(boolean arts) {
        this.arts = arts;
    }

    public boolean isFashion() {
        return fashion;
    }

    public void setFashion(boolean fashion) {
        this.fashion = fashion;
    }

    public boolean isSports() {
        return sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
    }


    @Override
    public String toString() {
        return "{ 'begin_date': " + beginDate + ",'sort': " + sort + "'arts': " +arts + "'fashion'" + fashion + "'sports'" + sports + "}";
    }
}
