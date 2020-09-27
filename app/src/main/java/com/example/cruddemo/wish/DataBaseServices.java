package com.example.cruddemo.wish;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}
