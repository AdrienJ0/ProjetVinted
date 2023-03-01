package com.example.projetvinted.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetvinted.Classes.Article;
import com.example.projetvinted.Interfaces.SelectListener;
import com.example.projetvinted.R;

import java.util.ArrayList;

public class AccountArticleAdapter extends RecyclerView.Adapter<AccountArticleAdapter.ViewHolder>{
    private ArrayList<Article> articleList;
    private Context context;
    private SelectListener listener;

    public AccountArticleAdapter(ArrayList<Article> articleList, Context context){
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.article_item_account, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.articleName.setText(article.getArticleName());
        holder.articlePrice.setText(article.getArticlePrice());

        Glide.with(context).load(articleList.get(position).getArticleImage()).into(holder.articleImage);
    }

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
            articleImage = itemView.findViewById(R.id.articleImageAcc);
            articleName = itemView.findViewById(R.id.articleNameAcc);
            articlePrice = itemView.findViewById(R.id.articlePriceAcc);
            cardView = itemView.findViewById((R.id.cardViewAccount));
        }

    }
}
