package com.example.cruddemo.wish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditWishActivity extends AppCompatActivity {

    private static final String TAG = "EditWishActivity";
    private static final String TAG2 = "CategorySpinner";
    EditText editDesc, editName;
    Spinner editCate;

    private DataBaseServices dataBaseServices = new DataBaseServices();
    DatabaseReference categoryList = dataBaseServices.getCategoriesRef();
    DatabaseReference newWishRef = dataBaseServices.getWishesRef();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_wish);
        Log.d(TAG, "onCreate: Activity started");
        Intent intent = getIntent();
        Wish thisWish = intent.getParcelableExtra("theWish");


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

        editName.setText(thisWish.getWishName());
        editDesc.setText(thisWish.getWishDesc());

    }
}
