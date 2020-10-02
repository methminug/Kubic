package com.example.cruddemo.wish;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.cruddemo.R;
import com.example.cruddemo.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DataBaseServices {

    private static final String TAG = "DELETING";

    private FirebaseDatabase thisdatabaseinstance = FirebaseDatabase.getInstance();
    private DatabaseReference wishesRef = thisdatabaseinstance.getReference("Wishes");
    // TODO                                                            CHANGE TO <<  Users  >>
    private DatabaseReference usersRef = thisdatabaseinstance.getReference("TestUsers");
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

        DatabaseReference usersdatabaseReference = this.usersRef.child(uid);
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

    public void deleteWish(Wish deletedWish, Context appContext){

        final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance().getReference().getStorage();

        //Get image URL in firebaseStorage
        final String imgURL = deletedWish.getImageURL();

        StorageReference photoref =firebaseStorage.getReferenceFromUrl(imgURL);

        photoref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG,"Successfully deleted image "+imgURL);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG,"UNABLE to delete image "+imgURL);
            }
        });

        //Deleting from Firebase database
        wishesRef.child(deletedWish.getWishKey()).removeValue();
        usersRef.child(deletedWish.getWishOwner()).child("myWishes").child(deletedWish.getWishKey()).removeValue();


        Log.i(TAG,"Deleted wish "+deletedWish.getWishKey());
        Toast.makeText(appContext,"Wish deleted successfully",Toast.LENGTH_LONG).show();

    }
}
