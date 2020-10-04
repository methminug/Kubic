package com.example.cruddemo.wish;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cruddemo.R;
import com.example.cruddemo.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.AccessController;
import java.util.ArrayList;

public class FirstFragment extends Fragment implements MyAdapter.OnWishListener{

    DatabaseReference databaseReference;
    DataBaseServices dataBaseServices = new DataBaseServices();

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

        databaseReference = dataBaseServices.getWishesRef();
        final MyAdapter.OnWishListener listener = this;

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allWishes.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    final Wish wish = dataSnapshot.getValue(Wish.class);
                    allWishes.add(wish);
                }
                myAdapter = new MyAdapter(view.getContext(),allWishes,0,listener);
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

    @Override
    public void OnWishClick(int position) {
        Wish thisWish = allWishes.get(position);
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.userdisplay_dialog);

        ImageView dialogImg = dialog.findViewById(R.id.userAvatar);
        TextView usernametxt = dialog.findViewById(R.id.userNameDisp);
        TextView userphone = dialog.findViewById(R.id.userPhoneDisp);

        Log.i("wishowner",thisWish.getWishOwner());

        dataBaseServices.getAUserDialog(getContext(),thisWish.getWishOwner(), usernametxt, userphone,dialogImg);

        dialog.show();

    }

    @Override
    public void OnWishClickDelete(int position) {
        // NOT IMPLEMENTED FOR THIS FRAGMENT
    }
}