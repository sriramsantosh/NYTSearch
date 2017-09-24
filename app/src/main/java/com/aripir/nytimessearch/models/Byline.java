package com.aripir.nytimessearch.models;

/**
 * Created by saripirala on 9/24/17.
 */

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Byline implements Serializable
{

    @SerializedName("original")
    @Expose
    private Object original;
    private final static long serialVersionUID = -4097887535670389404L;

    public Object getOriginal() {
        return original;
    }

    public void setOriginal(Object original) {
        this.original = original;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
