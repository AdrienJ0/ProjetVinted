package com.example.projetvinted.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.projetvinted.Classes.Article;
import com.example.projetvinted.R;

/***
 * Class which is associated to the layout fragment_article_details
 */

public class ArticleDetailsFragment extends Fragment {

    private Article article;
    private TextView articleName;
    private TextView articlePrice;
    private ImageView articleImage;

    public ArticleDetailsFragment(Article article) {
        this.article = article;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article_details, container, false);

        articleName = rootView.findViewById(R.id.articleNameDetails);
        articlePrice = rootView.findViewById(R.id.articlePriceDetails);
        articleImage = rootView.findViewById(R.id.articleImageDetails);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        articleName.setText(article.getArticleName());
        articlePrice.setText(article.getArticlePrice());
        Glide.with(getContext()).load(article.getArticleImage()).into(articleImage);
    }

    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }
}
