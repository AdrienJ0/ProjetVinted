package com.example.projetvinted.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetvinted.Classes.Article;
import com.example.projetvinted.Interfaces.SelectListener;
import com.example.projetvinted.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private ArrayList<Article> articleList;
    private Context context;
    private SelectListener listener;




    /*public ArticleAdapter(@NonNull FirebaseRecyclerOptions<Article> options) {
        super(options);
    }*/

    /*public ArticleAdapter(ArrayList<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;

        //this.listener = listener;
    }*/

    public ArticleAdapter(ArrayList<Article> articleList, Context context){
        this.articleList = articleList;
        this.context = context;
    }

    public ArticleAdapter(ArrayList<Article> articleList, Context context, SelectListener listener) {
        this.articleList = articleList;
        this.context = context;

        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.article_item_cardview, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.articleName.setText(article.getArticleName());
        holder.articlePrice.setText(article.getArticlePrice());

        Glide.with(context).load(articleList.get(position).getArticleImage()).into(holder.articleImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(article);
            }

        });
    }

    /*@Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Article article) {
        //Article article = articleList.get(position);
        holder.articleName.setText(article.getArticleName());
        holder.articlePrice.setText(article.getArticlePrice());
        holder.articleImage.setImageResource(article.getArticleImage());

        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                listener.onItemClicked(article);
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView articleImage;
        TextView articleName;
        TextView articlePrice;
        CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            articleImage = itemView.findViewById(R.id.articleImage);
            articleName = itemView.findViewById(R.id.articleName);
            articlePrice = itemView.findViewById(R.id.articlePrice);
            cardView = itemView.findViewById((R.id.cardView));
        }

    }
}
