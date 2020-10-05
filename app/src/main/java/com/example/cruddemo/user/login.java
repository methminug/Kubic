package com.example.cruddemo.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    TextView butSign;
    EditText txtUName,txtPassword;
    Button butLogin;

    FirebaseDatabase database;
    DatabaseReference users;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUName = findViewById(R.id.uname);
        txtPassword = findViewById(R.id.pwd);

        butLogin = findViewById(R.id.butIcon);
        butSign = findViewById(R.id.butSignUp);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");



        butSign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent s = new Intent(getApplicationContext(), signup.class);
                startActivity(s);
            }

            });

        butLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                logIn(txtUName.getText().toString(),
                        txtPassword.getText().toString());

            }
        });

    }

    private void logIn(final String username, final String password) {

        Query query = users.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        if(!username.isEmpty()){
                            Users login = dataSnapshot.getValue(Users.class);
                            if(login.getPassword().equals(password)) {
                                Toast.makeText(getApplicationContext(), "Successful Login", Toast.LENGTH_SHORT).show();

                                //Store username of currently logged in user
                                sharedPreferences = getSharedPreferences("SWOPsharedPreferences", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("currentUser",dataSnapshot.getKey());
                                editor.apply();
                                Log.i("User logged in ",dataSnapshot.getKey());

                                //GOES TO PROFILE

                                Intent intent = new Intent(getApplicationContext(),profile.class);
                                intent.putExtra("theUser",login);
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Password is Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Username is not registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Sorry, something went wrong. Please try again", Toast.LENGTH_SHORT).show();
                Log.i("Database error", String.valueOf(error));
            }
        });

    }
}

