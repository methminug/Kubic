package com.example.cruddemo.user;

import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

public class details extends AppCompatActivity {

    TextView txtName, txtAdd, txtEmail, txtPhone;
    DatabaseReference dbRef;
    Button showBut;
    Users usd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtName = findViewById(R.id.EtInputName);
        txtAdd = findViewById(R.id.EtInputAddress);
        txtEmail = findViewById(R.id.EtInputEmail);
        txtPhone = findViewById(R.id.EtInputPhone);

        showBut = findViewById(R.id.btnShow);

        usd = new Users();

        showBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Users").child("usd1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChildren()) {
                            txtName.setText(snapshot.child("name").getValue().toString());
                            txtAdd.setText(snapshot.child("address").getValue().toString());
                            txtEmail.setText(snapshot.child("email").getValue().toString());
                            txtPhone.setText(snapshot.child("phone").getValue().toString());
                        } else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}
