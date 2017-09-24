package com.aripir.nytimessearch.models;

/**
 * Created by saripirala on 9/24/17.
 */


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meta implements Serializable
{

    @SerializedName("hits")
    @Expose
    private Integer hits;
    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("time")
    @Expose
    private Integer time;
    private final static long serialVersionUID = -506891210423554240L;

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
