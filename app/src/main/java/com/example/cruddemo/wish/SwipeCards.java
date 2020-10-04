package com.example.cruddemo.wish;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
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
    SharedPreferences sharedPreferences;

    float x1,x2;
    int thisItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_cards);

        sharedPreferences = getSharedPreferences("SWOPsharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUser","-MITimME3wm7nA8CTDSO");
        editor.apply();

        swipeCardsView = findViewById(R.id.swipeCardsView);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);

        swipeCardsView.setCardsSlideListener(new SwipeCardsView.CardsSlideListener() {
            @Override
            public void onShow(int index) {
                thisItem = index;
            }

            @Override
            public void onCardVanish(int index, SwipeCardsView.SlideType type) {
                /*No implementation*/
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
                /*No implementation*/
            }
        });

        getItemData();

    }


    public void swipeRight(int index, String thisUser){

        BarterItem liked = barterItems.get(index);
        String key = liked.getItemKey();
        String name = liked.getName();

        dataBaseServices.AddToLiked(key, thisUser);
        Log.i("SWIPE EVENT",thisUser+" swiped right on "+name);
        Toast.makeText(getApplicationContext(), name+" liked! Item saved", Toast.LENGTH_SHORT).show();
    }

    public boolean onTouchEvent(MotionEvent touchevent){

        sharedPreferences = getApplicationContext().getSharedPreferences("SWOPsharedPreferences", Context.MODE_PRIVATE);
        final String thisUser =sharedPreferences.getString("currentUser","");

        switch (touchevent.getAction())
        {
            // Get x coordinate when user first touches the screen
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                break;
            }
            // Get x coordinate when user finishes sweep
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();

                //left to right sweep
                if (x1 < x2)
                {
                    swipeRight(thisItem,thisUser);
                    Log.i("Item","saved");
                }

                //right to left sweep
                if (x1 > x2)
                {
                    Log.i("Item","dismissed");
                }

                break;
            }
        }
        return false;
    }

    private void getItemData() {

        DatabaseReference allItems = dataBaseServices.getItemsRef();

        allItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                barterItems.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    BarterItem barterItem = new BarterItem();
                    barterItem = dataSnapshot.getValue(BarterItem.class);
                    barterItem.setItemKey(dataSnapshot.getKey());

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