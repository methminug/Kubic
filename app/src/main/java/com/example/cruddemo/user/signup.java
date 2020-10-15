package com.example.cruddemo.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cruddemo.R;
import com.example.cruddemo.wish.EmailValidator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    EditText txtName,txtAdd,txtEmail,txtPhone,txtPassword;
    Button butSign;
    DatabaseReference dbRef;
    Users usd;
    EmailValidator mEmailValidator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtName = findViewById(R.id.EtInputName);
        txtAdd = findViewById(R.id.EtInputAddress);
        txtEmail = findViewById(R.id.EtInputEmail);
        txtPhone = findViewById(R.id.EtInputPhone);
        txtPassword = findViewById(R.id.EtInputPassword);

        butSign = findViewById(R.id.btnSign);
        mEmailValidator = new EmailValidator();
        txtEmail.addTextChangedListener(mEmailValidator);

        usd = new Users();

        butSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
                try {
                    if (!mEmailValidator.isValid()) {
                        txtEmail.setError("Please enter a valid email.");
                        Log.i("User", "Not saving user information: Invalid name format");
                    }else {
                        if (TextUtils.isEmpty(txtName.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Empty Name", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(txtAdd.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Empty Address", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Empty Email", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(txtPhone.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Empty Phone", Toast.LENGTH_SHORT).show();
                        else if (TextUtils.isEmpty(txtPassword.getText().toString()))
                            Toast.makeText(getApplicationContext(), "Empty Password", Toast.LENGTH_SHORT).show();
                        else if (txtPassword.length() < 6) {
                            Toast.makeText(getApplicationContext(), "Password Must be >= 6 Characters", Toast.LENGTH_SHORT).show();
                        } else {

                            usd.setUsername(txtName.getText().toString().trim());
                            usd.setAddress(txtAdd.getText().toString().trim());
                            usd.setEmail(txtEmail.getText().toString().trim());
                            usd.setPhone(txtPhone.getText().toString().trim());
                            usd.setPassword(txtPassword.getText().toString().trim());

                            dbRef.push().setValue(usd);

                            //dbRef.child(usd.getUsername()).setValue(usd);
                            Toast.makeText(getApplicationContext(), "Successfully inserted", Toast.LENGTH_SHORT).show();
                            clearControls();

                            //GOES TO LOGIN PAGE
                            Intent intent = new Intent(getApplicationContext(), login.class);
                            startActivity(intent);
                        }
                    }
                }
                catch (NumberFormatException nfe){
                    Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();

                }

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
