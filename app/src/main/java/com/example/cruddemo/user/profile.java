package com.example.cruddemo.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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

public class profile extends AppCompatActivity {

    EditText txtName, txtAdd, txtEmail, txtPhone, txtPassword;
    Button butUpdate, butDelete;
    DatabaseReference dbRef;
    Users usd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.PName);
        txtAdd = findViewById(R.id.PAddress);
        txtEmail = findViewById(R.id.PEmail);
        txtPhone = findViewById(R.id.PPhone);
        txtPassword = findViewById(R.id.PPassw);

        butUpdate = findViewById(R.id.butUp);
        butDelete = findViewById(R.id.butDel);

        usd = new Users();

        final SharedPreferences preferences = getSharedPreferences("SWOPsharedPreferences", MODE_PRIVATE);
        final String currUser =preferences.getString("currentUser","");

        butUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Users");


                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(currUser)){
                            try{
                                usd.setUsername(txtName.getText().toString().trim());
                                usd.setAddress(txtAdd.getText().toString().trim());
                                usd.setEmail(txtEmail.getText().toString().trim());
                                usd.setPhone(Integer.parseInt(txtPhone.getText().toString().trim()));
                                usd.setPassword(txtPassword.getText().toString().trim());


                                dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currUser);
                                dbRef.setValue(usd);
                                clearControls();
                                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No Source To Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Users");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(currUser)){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currUser);
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                        else
                            Toast.makeText(getApplicationContext(), "No Source To Delete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });


    }


    private void clearControls(){
        txtName.setText("");
        txtAdd.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
    }
}
