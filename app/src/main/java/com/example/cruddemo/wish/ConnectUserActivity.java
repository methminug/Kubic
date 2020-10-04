package com.example.cruddemo.wish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cruddemo.R;

public class ConnectUserActivity extends AppCompatActivity {

    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_user);
        Intent intent = getIntent();
        String uname = intent.getStringExtra("theUser");
        username = findViewById(R.id.userNametxt);
        username.setText(uname);
    }
}