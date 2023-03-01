package com.example.projetvinted.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projetvinted.Classes.Article;
import com.example.projetvinted.MainActivity;
import com.example.projetvinted.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

/***
 * Class which is associated to the layout fragment_add_article
 */
public class AddArticleFragment extends Fragment {

    ImageView imageSelected;
    Uri imageUri;
    String imageUrl;
    EditText articleName;
    EditText articlePrice;
    Button btnInsertArticle;
    Button btnAddImage;

    FirebaseAuth auth;
    String user;

    String id;

    DatabaseReference database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_article, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser().getUid().toString();

        articleName = view.findViewById(R.id.articleNameAdd);
        articlePrice = view.findViewById(R.id.articlePriceAdd);
        btnInsertArticle = view.findViewById(R.id.btnInsertArticle);
        btnAddImage = view.findViewById(R.id.btnAddImage);

        imageSelected = view.findViewById(R.id.articleImageSelected);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            Intent data = result.getData();
                            imageUri = data.getData();
                            imageSelected.setImageURI(imageUri);
                        }
                        else{
                            Toast.makeText(getContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        database = FirebaseDatabase.getInstance().getReference().child("article");

        btnInsertArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToStorage();
            }
        });

        return view;
    }

    private void insertAticle(){
        String name = articleName.getText().toString();
        String price = articlePrice.getText().toString() + " $";

        id = database.push().getKey();

        Article article = new Article(id, user, name, price, imageUrl);
        database.setValue(article).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(getContext(), "Article ajouté", Toast.LENGTH_SHORT).show();
    }

    /*public void openGallery(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }*/

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK){
            imageUri = data.getData();
            Picasso.with(getContext()).load(imageUri).into(imageSelected);

            addToStorage(imageUri);
        }
    }*/

    private void addToStorage(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Images").child(imageUri.getLastPathSegment());

        /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.)*/

        //final StorageReference imageName = storageReference.child("ImageAdded");
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                insertAticle();

                /*imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        uri.toString();

                    }
                });*/
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Erreur!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
