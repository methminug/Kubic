package com.example.cruddemo.wish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddNewWish extends AppCompatActivity {

    //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SWOPsharedPreferences", MODE_PRIVATE);
    //SharedPreferences.Editor preferencesEdit = sharedPreferences.edit().putString("currentUser","uid12931");
    SharedPreferences sharedPreferences;

    private EditText txtname, txtdesc;
    private Spinner txtcate;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Wishes");
    private DatabaseReference thisUser = database.getReference("Users");
    private DatabaseReference categories = database.getReference("Categories");

    private Wish newWish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ADD USERid AND NAVIGATION!!!!!!!!!

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_wish);

        sharedPreferences = getApplicationContext().getSharedPreferences("SWOPsharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUser","uid12931");
        editor.apply();



        Query query = categories.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> categoryList = new ArrayList<String>();
                for (DataSnapshot datasnap1 : snapshot.getChildren()){
                    String categoryTitle = datasnap1.getKey().toString();
                    categoryList.add(categoryTitle);
                }

                ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(AddNewWish.this, android.R.layout.simple_spinner_item, categoryList);
                arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                txtcate.setAdapter(arrAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        txtname = findViewById(R.id.editTextItemName);
        txtdesc = findViewById(R.id.editTextItemDesc);
        txtcate = findViewById(R.id.categorySpinner);

        Button btnsave = findViewById(R.id.button_post);

        newWish = new Wish();

        btnsave.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {

                String currUser =sharedPreferences.getString("currentUser","");
                final Intent intent = new Intent(view.getContext(), WishList.class);

                if (TextUtils.isEmpty(txtname.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(txtdesc.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a description", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(txtcate.getSelectedItem().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a category", Toast.LENGTH_SHORT).show();
                }else{
                    newWish.setWishName(txtname.getText().toString().trim());
                    newWish.setWishDesc(txtdesc.getText().toString().trim());
                    newWish.setWishCategory(txtcate.getSelectedItem().toString().trim());
                    newWish.setWishOwner(currUser);

                    DatabaseReference anewWish = myRef.push();
                    anewWish.setValue(newWish);
                    String newWishID = anewWish.getKey();

                    thisUser.child(currUser).child("myWishes").child(newWishID).setValue(new Boolean(true));

                    Toast.makeText(getApplicationContext(), "sent message ID: "+newWishID, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

    }
}