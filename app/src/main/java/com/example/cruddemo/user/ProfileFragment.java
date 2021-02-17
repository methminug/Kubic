package com.example.cruddemo.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cruddemo.R;
import com.example.cruddemo.wish.DataBaseServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    EditText txtName, txtAdd, txtEmail, txtPhone, txtPassword;
    Button butUpdate, butDelete;
    DatabaseReference dbRef;
    Users usd;

    DataBaseServices dataBaseServices = new DataBaseServices();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container,false);

        txtName = view.findViewById(R.id.PName);
        txtAdd = view.findViewById(R.id.PAddress);
        txtEmail = view.findViewById(R.id.PEmail);
        txtPhone = view.findViewById(R.id.PPhone);
        txtPassword = view.findViewById(R.id.PPassw);

        butUpdate = view.findViewById(R.id.butUp);
        butDelete = view.findViewById(R.id.butDel);

        usd = new Users();

        final SharedPreferences preferences = view.getContext().getSharedPreferences("SWOPsharedPreferences", Context.MODE_PRIVATE);
        final String currUser =preferences.getString("currentUser","");

        DatabaseReference user = dataBaseServices.getUsersRef2().child(currUser);
        Log.i("curr",currUser);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Users thisUser = snapshot.getValue(Users.class);
                    txtName.setText(thisUser.getUsername());
                    txtAdd.setText(thisUser.getAddress());
                    txtEmail.setText(thisUser.getEmail());
                    txtPhone.setText(thisUser.getPhone());
                    txtPassword.setText(thisUser.getPassword());
//                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                            Users thisUser = dataSnapshot.getValue(Users.class);
//
//                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        butUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                dataBaseServices.getUsersRef2().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(currUser)){
                            try{
                                usd.setUsername(txtName.getText().toString().trim());
                                usd.setAddress(txtAdd.getText().toString().trim());
                                usd.setEmail(txtEmail.getText().toString().trim());
                                usd.setPhone(txtPhone.getText().toString().trim());
                                usd.setPassword(txtPassword.getText().toString().trim());


                                dbRef = dataBaseServices.getUsersRef2().child(currUser);
                                dbRef.setValue(usd);
                                Toast.makeText(view.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e){
                                Toast.makeText(view.getContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(view.getContext(), "No Source To Update", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
        butDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Users");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(currUser)){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currUser);
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(view.getContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(view.getContext(), "No Source To Delete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });
        return view;
    }

    private void clearControls(){
        txtName.setText("");
        txtAdd.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
    }

}
