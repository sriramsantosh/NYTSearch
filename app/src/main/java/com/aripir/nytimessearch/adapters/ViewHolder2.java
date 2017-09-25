package com.aripir.nytimessearch.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aripir.nytimessearch.R;
import com.aripir.nytimessearch.activities.CustomChromeActivity;
import com.aripir.nytimessearch.activities.NoInternetConnectionActivity;
import com.aripir.nytimessearch.models.Article;
import com.aripir.nytimessearch.util.CommonLib;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by saripirala on 9/23/17.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder {

    public TextView getHeadline() {
        return headline;
    }

    public void setHeadline(TextView headline) {
        this.headline = headline;
    }

    public TextView getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(TextView newsCategory) {
        this.newsCategory = newsCategory;
    }

    private TextView headline;
    private TextView newsCategory;

    public ViewHolder2(View itemView, final List<Article> articles) {
        super(itemView);
        headline = (TextView) itemView.findViewById(R.id.headline2);
        newsCategory = (TextView) itemView.findViewById(R.id.newsCategory2);

        itemView.setOnClickListener(view -> {

            if(!CommonLib.isOnline()){
                Intent intent = new Intent(view.getContext(), NoInternetConnectionActivity.class);
                intent.putExtra("activityName", "ArticleActivity");
                view.getContext().startActivity(intent);
            }else {
                Intent intent = new Intent(view.getContext(), CustomChromeActivity.class);
                intent.putExtra("article", Parcels.wrap(articles.get(getLayoutPosition())));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(intent);
            }
        });
    }
}
