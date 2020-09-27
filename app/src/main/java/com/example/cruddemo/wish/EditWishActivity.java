package com.example.cruddemo.wish;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditWishActivity extends AppCompatActivity {

    private static final String TAG = "EditWishActivity";
    private static final String TAG2 = "CategorySpinner";
    EditText editDesc, editName;
    Spinner editCate;
    SharedPreferences sharedPreferences;
    String uploadedImg = null;
    ImageView editImg;

    private DataBaseServices dataBaseServices = new DataBaseServices();
    DatabaseReference categoryList = dataBaseServices.getCategoriesRef();
    DatabaseReference newWishRef = dataBaseServices.getWishesRef();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_wish);

        Log.d(TAG, "onCreate: Activity started");
        Intent intent = getIntent();
        final Wish thisWish = intent.getParcelableExtra("theWish");

        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        UploadImage imageUploadfrag = new UploadImage(thisWish.getImageURL());
        fragTransaction.add(R.id.fragment, imageUploadfrag);
        fragTransaction.commit();

        sharedPreferences = getApplicationContext().getSharedPreferences("SWOPsharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUser","uid12931");
        editor.apply();




        final String category = thisWish.getWishCategory();

        Query query = categoryList.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> categoryList = new ArrayList<String>();
                for (DataSnapshot datasnap1 : snapshot.getChildren()){
                    String categoryTitle = datasnap1.getKey().toString();
                    categoryList.add(categoryTitle);
                }

                ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(EditWishActivity.this, android.R.layout.simple_spinner_item, categoryList);
                arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editCate.setAdapter(arrAdapter);
                //Log.w(TAG2,category);
                for (int i=0;i<categoryList.size();++i){
                    //Log.w(TAG2,categoryList.get(i));
                    if(category.contentEquals(categoryList.get(i))){
                        Log.w(TAG2,"Found match");
                        editCate.setSelection(i);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editDesc = findViewById(R.id.editTextItemDesc);
        editName = findViewById(R.id.editTextItemName);
        editCate = findViewById(R.id.categorySpinner);
        editImg = findViewById(R.id.new_selected_image);

        editName.setText(thisWish.getWishName());
        editDesc.setText(thisWish.getWishDesc());
        Log.i("img URL",thisWish.getImageURL());
        //Uri thisImgURI = Uri.parse(thisWish.getImageURL());
        //Log.i("Parse URL",thisWish.getImageURL());

        //Glide.with(imageUploadfrag.getContext()).load(thisWish.getImageURL()).into(editImg);

        Button btnUpdate = findViewById(R.id.button_post);

        final Wish newWish = new Wish();

        btnUpdate.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                String currUser =sharedPreferences.getString("currentUser","");
                final Intent intent = new Intent(view.getContext(), WishList.class);

                if (TextUtils.isEmpty(editName.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(editDesc.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a description", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(editCate.getSelectedItem().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a category", Toast.LENGTH_SHORT).show();
                }else{
                    newWish.setWishName(editName.getText().toString().trim());
                    newWish.setWishDesc(editDesc.getText().toString().trim());
                    newWish.setWishCategory(editCate.getSelectedItem().toString().trim());
                    newWish.setWishOwner(currUser);
                    if(uploadedImg != null){
                        newWish.setImageURL(uploadedImg);
                    }


                    //get wish ID and update

                    newWishRef.child(thisWish.getWishKey()).setValue(newWish);
//                    DatabaseReference anewWish = newWishRef.push();
//                    anewWish.setValue(newWish);
//                    String newWishID = anewWish.getKey();

                    //thisUser.child(currUser).child("myWishes").child(newWishID).setValue(new Boolean(true));

                    Toast.makeText(getApplicationContext(), "Item updated"+thisWish.getWishKey(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });


    }
    public void sendUploadUrl (String strURL){
        uploadedImg = strURL;
    }
}
