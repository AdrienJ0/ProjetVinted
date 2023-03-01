package com.example.projetvinted.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetvinted.Adapters.AccountArticleAdapter;
import com.example.projetvinted.Adapters.ArticleAdapter;
import com.example.projetvinted.Classes.Article;
import com.example.projetvinted.Interfaces.SelectListener;
import com.example.projetvinted.Login;
import com.example.projetvinted.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/***
 * Class which is associated to the layout fragment_account
 */
public class AccountFragment extends Fragment {

    FirebaseAuth auth;
    String user;
    Button btnLogout;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference database;
    AccountArticleAdapter articleAdapter;
    private ArrayList<Article> articleList;

    private ArrayList<Article> searchList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        btnLogout = view.findViewById(R.id.logout);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid().toString();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        //layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //Set the recyclerView horizontal

        database = FirebaseDatabase.getInstance().getReference();

        articleList = new ArrayList<Article>();

        searchList = new ArrayList<Article>();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                //finish();
                }
            });

        //Clear ArrayList
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();
    }

    private void searchArticles(){
        for(Article article: articleList){
            if(article.getUser().equals(user)){
                searchList.add(article);
            }
        }
    }

    private void GetDataFromFirebase() {

        Query query = database.child("article");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Article article = new Article();
                    article.setArticleImage(snapshot.child("articleImage").getValue().toString());
                    article.setArticleName(snapshot.child("articleName").getValue().toString());
                    article.setArticlePrice(snapshot.child("articlePrice").getValue().toString());
                    article.setUser(snapshot.child("user").getValue().toString());
                    //article.setArticleId(snapshot.child("articleId").getValue().toString());

                    articleList.add(article);
                }

                searchArticles();

                articleAdapter = new AccountArticleAdapter(searchList, getContext());
                recyclerView.setAdapter((articleAdapter));
                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void ClearAll(){
        if(articleList != null){
            articleList.clear();

            if(articleAdapter !=  null){
                articleAdapter.notifyDataSetChanged();
            }
        }

        articleList =  new ArrayList<Article>();
    }

    private void deleteArticle(Article article){

        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(article.getArticleImage());
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                database.child("article").child("id");
            }
        });

    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
                //finish();
            }
        });
    }*/

}
