package com.example.cruddemo.wish;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.cruddemo.R;
import com.example.cruddemo.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataBaseServices {

    private FirebaseDatabase thisdatabaseinstance = FirebaseDatabase.getInstance();
    private DatabaseReference wishesRef = thisdatabaseinstance.getReference("Wishes");
    private DatabaseReference usersRef = thisdatabaseinstance.getReference("Users");
    private DatabaseReference categoriesRef = thisdatabaseinstance.getReference("Categories");

    public FirebaseDatabase getDatabase() {
        return thisdatabaseinstance;
    }

    public DatabaseReference getWishesRef() {
        return wishesRef;
    }

    public DatabaseReference getUsersRef() {
        return usersRef;
    }

    public DatabaseReference getCategoriesRef() {
        return categoriesRef;
    }

    public void getAUserDialog(final Context context, final String uid, final TextView username, final TextView userphone, final ImageView userPic) {

        DatabaseReference usersdatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
        usersdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String uname = snapshot.child("username").getValue().toString();
                String uphone = snapshot.child("phone").getValue().toString();
                String uprofile = null;
                try{
                    uprofile = snapshot.child("profilePic").getValue().toString();
                    Glide.with(context).load(uprofile).into(userPic);
                }catch (NullPointerException e){
                    Log.i("null","userprofile");
                    userPic.setImageResource(R.drawable.ic_person_outline_black_24);
                }

                if(uname != null){
                    username.setText(uname);
                    userphone.setText(uphone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
