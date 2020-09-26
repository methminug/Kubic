package com.example.cruddemo.wish;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    DatabaseReference databaseReference;

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    ArrayList<Wish> allWishes;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_first, container, false);

        mRecyclerView = view.findViewById(R.id.allWishRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        allWishes = new ArrayList<Wish>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Wishes");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    final Wish wish = dataSnapshot.getValue(Wish.class);
                    allWishes.add(wish);
                }
                myAdapter = new MyAdapter(view.getContext(),allWishes);
                mRecyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Database Error",error.getMessage());
                Toast.makeText(view.getContext(), "Sorry. Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

//    private ArrayList<BarterItem> getAllList(){
//
//        ArrayList<BarterItem> items = new ArrayList<>();
//
//        BarterItem wishItem = new BarterItem();
//        wishItem.setName("Fountain pen");
//        wishItem.setDescription("Blue Ink. Preferably Second hand");
//        wishItem.setCategory("Miscellaneous");
//        wishItem.setImg(R.drawable.pen);
//        items.add(wishItem);
//
//        return items;
//
//    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.gotoMywish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}