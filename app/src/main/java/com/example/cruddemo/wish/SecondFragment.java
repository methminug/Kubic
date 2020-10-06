package com.example.cruddemo.wish;

import android.app.Dialog;
import android.content.Context;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.ArrayList;

public class SecondFragment extends Fragment implements MyAdapter.OnWishListener {

    DatabaseReference databaseReference;
    DataBaseServices dataBaseServices = new DataBaseServices();

    private RecyclerView mRecyclerView;
    FirebaseFunctions firebaseFunctions;
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

        sharedPreferences = view.getContext().getSharedPreferences("SWOPsharedPreferences", Context.MODE_PRIVATE);
        final String thisUser =sharedPreferences.getString("currentUser","");

        mRecyclerView = view.findViewById(R.id.wishRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        myWishes = new ArrayList<Wish>();
        TextView title = getActivity().findViewById(R.id.pagetitle);
        title.setText("My wishes");

        // take a single wish object
        firebaseFunctions = FirebaseFunctions.getInstance();
        databaseReference = dataBaseServices.getWishesRef();
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
                Log.i("WishBtn","Create new wish clicked");
                //Start activity 2
                startActivity(intent);

            }
        });
    }


    @Override
    public void OnWishClick(int position) {
        Intent intent = new Intent(getContext(), EditWishActivity.class);
        //attach wish as parcelable
        intent.putExtra("theWish",myWishes.get(position));

        startActivity(intent);
    }

    @Override
    public void OnWishClickDelete(int position) {
        Wish thisWish = myWishes.get(position);
        DeleteWish dialog = new DeleteWish(thisWish, getContext(),this, position);
        dialog.showDeleteDialog();

    }

    public void wishDeleted(int position){
        myWishes.remove(myWishes.get(position));
        myAdapter.notifyDataSetChanged();
    }
}