package com.example.cruddemo.wish;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.huxq17.swipecardsview.SwipeCardsView;

import java.util.ArrayList;

public class SwipeCards extends AppCompatActivity {

    private SwipeCardsView swipeCardsView;
    private ArrayList<BarterItem> barterItems = new ArrayList<>();
    private DataBaseServices dataBaseServices = new DataBaseServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_cards);

        swipeCardsView = findViewById(R.id.swipeCardsView);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        getItemData();

        // TODO show end screen after card deck finishes
    }

    private void getItemData() {

        DatabaseReference allItems = dataBaseServices.getItemsRef();

        allItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                barterItems.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    final BarterItem barterItem = dataSnapshot.getValue(BarterItem.class);
                    barterItems.add(barterItem);
                }

                CardAdapter cardAdapter = new CardAdapter(getApplicationContext(), barterItems);
                swipeCardsView.setAdapter(cardAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Database Error",error.getMessage());
                Toast.makeText(getApplicationContext(), "Sorry. Something went wrong displaying the items.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}