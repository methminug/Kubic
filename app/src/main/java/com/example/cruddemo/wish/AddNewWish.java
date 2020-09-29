package com.example.cruddemo.wish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cruddemo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewWish extends AppCompatActivity {


    private EditText txtname, txtdesc, txtcate;
    private Button btnsave;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Wishes");
    private Wish newWish;
    //private FirebaseDatabase firebasedatabse = FirebaseDatabase.getInstance();
    //private DatabaseReference rootReference = firebasedatabse.getReference();
    //private DatabaseReference wishreference = rootReference.child("awish");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ADD USERid AND NAVIGATION!!!!!!!!!

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_wish);

        txtname = findViewById(R.id.editTextItemName);
        txtdesc = findViewById(R.id.editTextItemDesc);
        txtcate = findViewById(R.id.editTextItemCategory);

        btnsave = findViewById(R.id.button_post);

        newWish = new Wish();

        btnsave.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtname.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(txtdesc.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a description", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(txtcate.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Enter a category", Toast.LENGTH_SHORT).show();
                }else{
                    newWish.setWishName(txtname.getText().toString().trim());
                    newWish.setWishDesc(txtdesc.getText().toString().trim());
                    newWish.setWishCategory(txtcate.getText().toString().trim());

                    myRef.child("w02").setValue(newWish);

                    Toast.makeText(getApplicationContext(), "sent message", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");
//        Toast.makeText(getApplicationContext(), "sent message", Toast.LENGTH_SHORT).show();
    }
}