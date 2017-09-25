package com.aripir.nytimessearch.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.aripir.nytimessearch.R;
import com.aripir.nytimessearch.databinding.ActivityNoInternetBinding;
import com.aripir.nytimessearch.util.CommonLib;


/**
 * Created by saripirala on 9/23/17.
 */

public class NoInternetConnectionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView;

    private ActivityNoInternetBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_no_internet);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
         setSupportActionBar(toolbar);

        imageView = binding.imageView;

        Drawable myDrawable = getResources().getDrawable(R.drawable.no_connection);
        imageView.setImageDrawable(myDrawable);
    }

    public void checkForInternet(View view) {

        if(CommonLib.isOnline()){
            Toast.makeText(this, "Internet is back up !", Toast.LENGTH_SHORT).show();
            Intent intent =  new Intent(this, SearchActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Internet is still down :(", Toast.LENGTH_SHORT).show();
        }
    }

}
