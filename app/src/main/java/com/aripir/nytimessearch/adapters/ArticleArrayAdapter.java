package com.aripir.nytimessearch.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aripir.nytimessearch.models.Article;
import com.aripir.nytimessearch.R;
import com.aripir.nytimessearch.activities.ArticleActivity;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by saripirala on 9/19/17.
 */

public class ArticleArrayAdapter extends  RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder>{

    private Context context;
    private List<Article> articles;

    public ArticleArrayAdapter(Context context, List<Article> articles)
    {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.bind(article);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private ImageView imageView;
        private TextView headline;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.articleImage);
            headline = (TextView) itemView.findViewById(R.id.headline);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    Intent intent = new Intent(context, ArticleActivity.class);
                    intent.putExtra("article", Parcels.wrap(articles.get(itemPosition)));
                    context.startActivity(intent);
                    //Toast.makeText(context, itemPosition + ":" + String.valueOf(headline.getText()), Toast.LENGTH_SHORT).show();
                }
            });

        }

        // Involves populating data into the item through holder
        public void bind(final Article article) {
            imageView.setImageResource(0);
            headline.setText(article.getHeadline());

            String thumbNail = article.getThumbNail();

            String publishDate = article.getPublishDate();

            if (thumbNail != null && !thumbNail.isEmpty()) {

                if(publishDate.contains("22")){

                     int a = imageView.getLayoutParams().height;
                    int b = imageView.getLayoutParams().width;

                    imageView.getLayoutParams().height = 720;
                    imageView.getLayoutParams().width = 540;
                    headline.setTextColor(Color.WHITE);

//                    int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 135,  getResources().getDisplayMetrics());
//                    imageView.getLayoutParams().height = dimensionInDp;
//                    imageView.getLayoutParams().width = dimensionInDp;
                    imageView.requestLayout();

//                    Glide.with(context)
//                            .load(Uri.parse(thumbNail))
//                            .override(720, 480)
//                            .into(imageView);
                    }
                //else
                    Glide.with(context)
                        .load(Uri.parse(thumbNail))
                        .centerCrop()
                        .into(imageView);

//                Picasso.with(context).load(thumbNail).fit().centerCrop()
//                        .into(imageView);
            }
        }

    }
}
