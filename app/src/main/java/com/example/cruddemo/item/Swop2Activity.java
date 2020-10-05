package com.example.cruddemo.item;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cruddemo.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Swop2Activity extends AppCompatActivity {

    private EditText textView2;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swop2);
        Items item = new Items();
        showUpdateDialog(item.getId(), item.getName());

    }

    private void updateItem(String id, String name) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Items");
        dR.child("Items").child(id);
        Items item = new Items(name);
        dR.setValue(item);
        Toast.makeText(getApplicationContext(), "Item name Updated", Toast.LENGTH_LONG).show();
    }

    private void showUpdateDialog(final String id, String name) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) findViewById(R.id.name);
//        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.update);

        dialogBuilder.setTitle(name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                updateItem(id, name);

            }
        });


    }








}