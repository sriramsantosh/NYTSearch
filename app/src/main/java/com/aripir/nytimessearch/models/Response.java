package com.aripir.nytimessearch.models;

/**
 * Created by saripirala on 9/24/17.
 */

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable
{

    @SerializedName("docs")
    @Expose
    private List<Doc> docs = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    private final static long serialVersionUID = 3148056449349976603L;

    public List<Doc> getDocs() {
        return docs;
    }

    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}