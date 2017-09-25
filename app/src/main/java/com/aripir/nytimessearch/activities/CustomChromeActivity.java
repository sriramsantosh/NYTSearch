package com.aripir.nytimessearch.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aripir.nytimessearch.R;
import com.aripir.nytimessearch.models.Article;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CustomChromeActivity extends AppCompatActivity {

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_chrome);

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        // set toolbar color
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorSanMarino));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_name);

        article = Parcels.unwrap(getIntent().getParcelableExtra("article"));

        String url = article.getWebUrl();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);

        int requestCode = 100;

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        CustomTabsIntent customTabsIntent = builder.build();
        builder.setActionButton(bitmap, "Share Link", pendingIntent, true);
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
