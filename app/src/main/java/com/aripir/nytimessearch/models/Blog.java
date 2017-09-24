package com.aripir.nytimessearch.models;

/**
 * Created by saripirala on 9/24/17.
 */

import com.google.gson.Gson;

import java.io.Serializable;

public class Blog implements Serializable
{

    private final static long serialVersionUID = -6498463030628188371L;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
