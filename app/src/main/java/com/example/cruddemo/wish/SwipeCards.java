package com.example.cruddemo.wish;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cruddemo.R;
import com.huxq17.swipecardsview.SwipeCardsView;

import java.util.ArrayList;

public class SwipeCards extends AppCompatActivity {

    private SwipeCardsView swipeCardsView;
    private ArrayList<BarterItem> barterItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_cards);

        swipeCardsView = findViewById(R.id.swipeCardsView);
        swipeCardsView.retainLastCard(false);
        swipeCardsView.enableSwipe(true);
        getItemData();
    }

    private void getItemData() {

        BarterItem wishItem = new BarterItem();
        wishItem.setName("Cabinet");
        wishItem.setDescription("Handmade out of wood");
        wishItem.setCategory("Negotiable");
        wishItem.setImg(R.drawable.cabinet);
        barterItems.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Pedestal fan");
        wishItem.setDescription("5\' tall, in good condition");
        wishItem.setCategory("Similar item or negotiable");
        wishItem.setImg(R.drawable.fan);
        barterItems.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Stool");
        wishItem.setDescription("Made of any teak wood");
        wishItem.setCategory("Furniture");
        wishItem.setImg(R.drawable.stool);
        barterItems.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Tool Set");
        wishItem.setDescription("Can't find this design anywhere!");
        wishItem.setCategory("Miscellaneous");
        wishItem.setImg(R.drawable.toolbox);
        barterItems.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Small armchair");
        wishItem.setDescription("A small one, maybe something similar to the picture?");
        wishItem.setCategory("Furniture");
        wishItem.setImg(R.drawable.chair);
        barterItems.add(wishItem);

        CardAdapter cardAdapter = new CardAdapter(this, barterItems);
        swipeCardsView.setAdapter(cardAdapter);

    }
}