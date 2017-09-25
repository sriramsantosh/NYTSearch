package com.aripir.nytimessearch.adapters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aripir.nytimessearch.models.Article;
import com.aripir.nytimessearch.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by saripirala on 9/19/17.
 */

public class ArticleArrayAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;

    private List<Article> articles;

    private final int IMAGE_HEADLINE = 0, HEADLINE = 1;


    public ArticleArrayAdapter(Context context, List<Article> articles)
    {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case IMAGE_HEADLINE:
                View v1 = inflater.inflate(R.layout.item_article_result, parent, false);
                viewHolder = new ViewHolder1(v1, articles);
                break;
            case HEADLINE:
                View v2 = inflater.inflate(R.layout.item_article_result_2, parent, false);
                viewHolder = new ViewHolder2(v2, articles);
                break;
            default:
                View def = inflater.inflate(R.layout.item_article_result, parent, false);
                viewHolder = new ViewHolder1(def, articles);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case IMAGE_HEADLINE:
                ViewHolder1 vh1 = (ViewHolder1) viewHolder;
                configureViewHolder1(vh1, position);
                break;
            case HEADLINE:
                ViewHolder2 vh2 = (ViewHolder2) viewHolder;
                configureViewHolder2(vh2, position);
                break;
        }
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {

        Article article = articles.get(position);
        if (article != null) {

            vh1.getImageView().setImageResource(0);
            vh1.getHeadline().setText(article.getHeadline());

            String thumbNail = article.getThumbNail();
            String publishDate = article.getPublishDate();
            String newsDesk = article.getNewsDesk().toLowerCase();

            setNewsDesk(vh1.getNewsCategory(), newsDesk);

            if (thumbNail != null && !thumbNail.isEmpty()) {

//                if(publishDate.contains("23")){
//
//               //     int a = vh1.getImageView().getLayoutParams().height;
//               //     int b = vh1.getImageView().getLayoutParams().width;
//
//                    vh1.getImageView().getLayoutParams().height = 720;
//                    vh1.getImageView().getLayoutParams().width = 540;
//                    vh1.getHeadline().setTextColor(Color.WHITE);
//                    vh1.getHeadline().setTypeface(vh1.getHeadline().getTypeface(), Typeface.BOLD);
//
//                    vh1.getImageView().requestLayout();
//                }
                Glide.with(context)
                        .load(Uri.parse(thumbNail))
                        .fitCenter()
                        .centerCrop()
                        .into(vh1.getImageView());
            }

        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        Article article = articles.get(position);
        if (article != null) {
            setNewsDesk(vh2.getNewsCategory(), article.getNewsDesk().toLowerCase());
            vh2.getHeadline().setText(article.getHeadline());
        }
    }

    private void setNewsDesk(TextView textView, String newsDesk){

        if(newsDesk.contains("sports")){
            textView.setBackgroundColor(Color.parseColor("#4469AF"));
            textView.setText("SPORTS");
        }else if(newsDesk.contains("arts")){
            textView.setBackgroundColor(Color.parseColor("#44B37F"));
            textView.setText("ARTS");
        }else if(newsDesk.contains("fashion")){
            textView.setBackgroundColor(Color.parseColor("#FDA137"));
            textView.setText("FASHION");
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    @Override
    public int getItemViewType(int position) {
        //More to come
        Article article = articles.get(position);
        if(article.getThumbNail()!=null && article.getThumbNail().isEmpty() )
            return HEADLINE;
        else
            return IMAGE_HEADLINE;
    }
}
