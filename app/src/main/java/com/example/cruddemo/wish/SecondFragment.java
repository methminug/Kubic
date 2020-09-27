package com.example.cruddemo.wish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecondFragment extends Fragment implements MyAdapter.OnWishListener {

    DatabaseReference databaseReference;

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    ArrayList<Wish> myWishes;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_second, container, false);

        //delete this
        sharedPreferences = view.getContext().getSharedPreferences("SWOPsharedPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentUser","uid12931");
        editor.apply();
        //

        final String thisUser =sharedPreferences.getString("currentUser","");

        mRecyclerView = view.findViewById(R.id.wishRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myWishes = new ArrayList<Wish>();

        // take a single wish object
        databaseReference = FirebaseDatabase.getInstance().getReference("Wishes");
        Query query = databaseReference.orderByChild("wishOwner").equalTo(thisUser);
        query.addListenerForSingleValueEvent(valueEventListener);

        myAdapter = new MyAdapter(view.getContext(),myWishes,1, this);
        mRecyclerView.setAdapter(myAdapter);

        return view;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            myWishes.clear();
            if(snapshot.exists()){
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Wish mywish = dataSnapshot.getValue(Wish.class);
                    mywish.setWishKey(dataSnapshot.getKey());
                    myWishes.add(mywish);
                }
                myAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.toMainList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("mainlist","Gone to main list");
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.addMywish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(view.getContext(), AddNewWish.class);
                Log.i("WishBtm","Create new wish clicked");
                //Start activity 2
                startActivity(intent);

            }
        });
    }


    @Override
    public void OnWishClick(int position) {
        Intent intent = new Intent(getContext(), EditWishActivity.class);
        intent.putExtra("theWish",myWishes.get(position));
        //attach wish as parcelable
        startActivity(intent);
    }
}