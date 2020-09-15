package com.example.cruddemo.wish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;

import java.util.ArrayList;

public class SecondFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_second, container, false);

        mRecyclerView = view.findViewById(R.id.wishRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        myAdapter = new MyAdapter(view.getContext(),getMyList());
        mRecyclerView.setAdapter(myAdapter);

        return view;
    }

    private ArrayList<BarterItem> getMyList(){

        ArrayList<BarterItem> items = new ArrayList<>();

        BarterItem wishItem = new BarterItem();
        wishItem.setName("Wooden Stool");
        wishItem.setDescription("Made of any kind of wood");
        wishItem.setCategory("Furniture");
        wishItem.setImg(R.drawable.stool);
        items.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Small armchair");
        wishItem.setDescription("A small one, maybe something similar to the picture?");
        wishItem.setCategory("Furniture");
        wishItem.setImg(R.drawable.chair);
        items.add(wishItem);

        return items;

    }

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


}