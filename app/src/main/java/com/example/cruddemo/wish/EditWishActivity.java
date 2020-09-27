package com.example.cruddemo.wish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cruddemo.R;

public class EditWishActivity extends AppCompatActivity {

    private static final String TAG = "EditWishActivity";
    TextView txtdesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wish);
        Log.d(TAG, "onCreate: Activity started");
        Intent intent = getIntent();
        String desc = intent.getStringExtra("theDesc");

        txtdesc = findViewById(R.id.txteditdesc);
        txtdesc.setText(desc);
    }
}
