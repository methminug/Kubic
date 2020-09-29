package com.example.cruddemo.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    EditText txtUName,txtPassword;
    Button butLogin,butSign;

    FirebaseDatabase database;
    DatabaseReference users;


    @SuppressLint("WrongViewCast")
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
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        Users login = snapshot.child(username).getValue(Users.class);
                        if(login.getPassword().equals(password)) {
                            Toast.makeText(login.this, "Succes Login", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(login.this, "Password is Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(login.this, "Username is not registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

