package com.example.cruddemo;

import androidx.appcompat.app.AppCompatActivity;
//package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


        EditText mFullName,mEmail,mPassword,mPhone;
        Button mRegisterBtn;
        TextView mLoginBtn;
        FirebaseAuth fAuth;
        ProgressBar progressBar;
        private FirebaseFirestore firebaseFirestore;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

//            mFullName = findViewById(R.id.name);
//            mEmail = findViewById(R.id.email);
//            mPassword = findViewById(R.id.password);
//            mPhone = findViewById(R.id.phone);
//            mRegisterBtn = findViewById(R.id.registerbtn);
//            mLoginBtn = findViewById(R.id.createText);

            fAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();

            progressBar = findViewById(R.id.progressBar);

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),ListActivity.class));
//            finish();
//        }


            mRegisterBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String email = mEmail.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    String name = mFullName.getText().toString().trim();
                    String number = mPhone.getText().toString().trim();

                    if(TextUtils.isEmpty(email)){
                        mEmail.setError("Email is Required");
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        mPassword.setError("Password is Required");
                        return;
                    }
                    if(password.length() < 8){
                        mPassword.setError("Password must 8 Characters");
                    }
                    if (TextUtils.isEmpty(name)){
                        mFullName.setError("Fill the FullName");
                        return;
                    }
                    if (number.length() == 9){
                        mPhone.setError("Fill the Phone Number");
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //User Register in Firebase

                    fAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        Map<Object,String> userdata = new HashMap<>();
                                        userdata.put("Full Name",mFullName.getText().toString());
                                        userdata.put("Password",mPassword.getText().toString());
                                        userdata.put("Phone Number",mPhone.getText().toString());//                                        userdata.put("Email",mEmail.getText().toString());

                                        firebaseFirestore.collection("Users")
                                                .add(userdata)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@androidx.annotation.NonNull Task<DocumentReference> task) {
                                                        if (task.isSuccessful()){
                                                            Toast.makeText(Register.this,"User Created",Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(),Login.class));
                                                        }else {
                                                            Toast.makeText(Register.this,"Erroe !!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                            progressBar.setVisibility(View.GONE);
                                                        }
                                                 }
                                               });
                                  }else {
                                           Toast.makeText(Register.this,"Error !!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                       progressBar.setVisibility(View.GONE);
                                  }
                               }
                            });
                }
            });


            mLoginBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),Login.class));
            }
            }
            );

        }

@Override
       public void onBackPressed() {
//           startActivity(new Intent(getApplicationContext(),PathActivity.class));
//           finish();
            super.onBackPressed();
       }
  }

