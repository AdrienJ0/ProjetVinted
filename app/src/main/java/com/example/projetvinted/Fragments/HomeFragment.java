package com.example.projetvinted.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetvinted.Adapters.ArticleAdapter;
import com.example.projetvinted.Classes.Article;
import com.example.projetvinted.Interfaces.SelectListener;
import com.example.projetvinted.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/***
 * Class which is associated to the layout fragment_home
 */
public class HomeFragment extends Fragment implements SelectListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference database;
    ArticleAdapter articleAdapter;
    private ArrayList<Article> articleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager); //Set the recyclerView horizontal

        database = FirebaseDatabase.getInstance().getReference();

        articleList = new ArrayList<Article>();

        //Clear ArrayList
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

    }

    private void GetDataFromFirebase() {

        Query query = database.child("article");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Article article = new Article();
                    article.setArticleImage(snapshot.child("Image").getValue().toString());
                    article.setArticleName(snapshot.child("Name").getValue().toString());
                    article.setArticlePrice(snapshot.child("Price").getValue().toString());

                    articleList.add(article);
                }

                articleAdapter = new ArticleAdapter(articleList, getContext(), new SelectListener() {
                    @Override
                    public void onItemClicked(Article article) {
                        Fragment fragment = new ArticleDetailsFragment(article);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, fragment, "Article Details Fragment");
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
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
    @Override
    public void onItemClicked(Article article) {
        Fragment fragment = new ArticleDetailsFragment(article);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "Article Details Fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
