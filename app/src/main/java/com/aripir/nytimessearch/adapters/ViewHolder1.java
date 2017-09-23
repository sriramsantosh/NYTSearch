package com.aripir.nytimessearch.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aripir.nytimessearch.R;
import com.aripir.nytimessearch.activities.ArticleActivity;
import com.aripir.nytimessearch.models.Article;

import org.parceler.Parcels;

import java.util.List;


/**
 * Created by saripirala on 9/23/17.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder {

    private ImageView imageView;
    private TextView headline;
    private TextView newsCategory;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

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

    public ViewHolder1(View itemView, final List<Article> articles) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.articleImage);
        headline = (TextView) itemView.findViewById(R.id.headline);
        newsCategory = (TextView) itemView.findViewById(R.id.newsCategory);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(view.getContext(), ArticleActivity.class);
                intent.putExtra("article", Parcels.wrap(articles.get(itemPosition)));
                view.getContext().startActivity(intent);
            }
        });

    }



}
