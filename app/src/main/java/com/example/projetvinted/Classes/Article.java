package com.example.projetvinted.Classes;

import com.google.firebase.auth.FirebaseUser;

/***
 * Class which contain all the data about an article
 *
 * */
public class Article {

    private String user;
    private String articleName;
    private String articlePrice;
    private String articleImage;
    private String articleId;
    //private ClassSeller articleSeller;

    public Article(){

    }

    public Article(String user, String  articleName, String articlePrice, String articleImage) {
        this.articleId = articleId;
        this.user = user;
        this.articleName = articleName;
        this.articlePrice = articlePrice;
        this.articleImage = articleImage;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(String articleImage) {
        this.articleImage = articleImage;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticlePrice() {
        return articlePrice;
    }

    public void setArticlePrice(String articlePrice) {
        this.articlePrice = articlePrice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
