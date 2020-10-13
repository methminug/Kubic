package com.example.cruddemo.wish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindMatch extends AppCompatActivity {

    ArrayList<String> checkedUsers;
    //Map<BarterItem,BarterItem> matches;
    List<List<List<String>>> matches = new ArrayList<List<List<String>>>();
    ArrayList<String> userSwipedOnList, ownerSwipedOnList;
    ArrayList<String> userItems, owneritems;
    ArrayList<String> allSwiped, wants;
    SharedPreferences sharedPreferences;
    DataBaseServices dataBaseServices = new DataBaseServices();

    TextView txtview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        txtview = findViewById(R.id.matchtxt);
        userSwipedOnList = new ArrayList<>();
        ownerSwipedOnList = new ArrayList<>();
        checkedUsers = new ArrayList<>();
        userItems = new ArrayList<>();
        owneritems = new ArrayList<>();
        wants = new ArrayList<>();
        allSwiped  = new ArrayList<>();

        sharedPreferences = getSharedPreferences("SWOPsharedPreferences", MODE_PRIVATE);
        final String currUser =sharedPreferences.getString("currentUser","");
        Log.i("Current user",currUser);
        getMatches(currUser);

    }

    public void getMatches(final String user){

        //getting current users list of items posted
        dataBaseServices.getUsersRef().child(user).child("myItems").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userItems.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dataSnapshot.getValue();
                    String aUserItem = dataSnapshot.getKey();
                    userItems.add(aUserItem);
                }

                //getting current users list of items swiped right on
                dataBaseServices.getUsersRef().child(user).child("swipedOn").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userSwipedOnList.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            dataSnapshot.getValue();
                            String swipedItem = dataSnapshot.getKey();
                            userSwipedOnList.add(swipedItem);
                        }

                        for (final String anItem:userSwipedOnList) {
                            // Retrieve owner of a swiped item
                            dataBaseServices.getItemsRef().child(anItem).child("offeredBy").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String swipedOwner = snapshot.getValue().toString();
                                    Log.i("Currently checking",swipedOwner);
                                    if(!checkedUsers.contains(swipedOwner)){
                                        checkedUsers.add(swipedOwner);
                                        //get item owner's posted item list
                                        dataBaseServices.getUsersRef().child(swipedOwner).child("myItems").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                owneritems.clear();
                                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                                    dataSnapshot.getValue();
                                                    String anOwnerItem = dataSnapshot.getKey();
                                                    owneritems.add(anOwnerItem);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                        //Get items owner swiped on
                                        dataBaseServices.getUsersRef().child(swipedOwner).child("swipedOn").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                ownerSwipedOnList.clear();
                                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                                    dataSnapshot.getValue();
                                                    String swipedItem = dataSnapshot.getKey();
                                                    ownerSwipedOnList.add(swipedItem);
                                                }

                                                wants.clear();
                                                for (String itemInOwnerList:ownerSwipedOnList) {
                                                    if(userItems.contains(itemInOwnerList)){
                                                        wants.add(itemInOwnerList);
                                                    }
                                                }
                                                allSwiped.clear();
                                                if(!wants.isEmpty()){
                                                    for (String itemSwiped:userSwipedOnList) {
                                                        if(owneritems.contains(itemSwiped)){
                                                            allSwiped.add(itemSwiped);
                                                        }
                                                    }

                                                    //List<ArrayList<String>> temp = new ArrayList<>();
                                                    List<List<String>> temp = new ArrayList<List<String>>();
                                                    Log.i("logged",allSwiped.get(0));
                                                    temp.add(allSwiped);
                                                    temp.add(wants);

                                                    matches.add(temp);


                                                    if(matches.isEmpty()){
                                                        txtview.setText("No Matches.\nTry swiping again!");
                                                    }else{
                                                        Log.i("Temp: ",Arrays.deepToString(temp.toArray()));
                                                        RecyclerView mRecyclerView = findViewById(R.id.itemPairs);
                                                        mRecyclerView.setLayoutManager(new LinearLayoutManager(FindMatch.this));

                                                        ItemPairsAdapter itemPairsAdapter = new ItemPairsAdapter(matches);
                                                        //Adding values to cards

                                                        mRecyclerView.setAdapter(itemPairsAdapter);

                                                        Log.i("Matched Items",Arrays.deepToString(matches.toArray()));
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}