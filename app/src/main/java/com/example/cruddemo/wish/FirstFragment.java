package com.example.cruddemo.wish;

import android.os.Bundle;
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

public class FirstFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        mRecyclerView = view.findViewById(R.id.allWishRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        myAdapter = new MyAdapter(view.getContext(),getAllList());
        mRecyclerView.setAdapter(myAdapter);

        return view;
    }

    private ArrayList<BarterItem> getAllList(){

        ArrayList<BarterItem> items = new ArrayList<>();

        BarterItem wishItem = new BarterItem();
        wishItem.setName("Fountain pen");
        wishItem.setDescription("Blue Ink. Preferably Second hand");
        wishItem.setCategory("Miscellaneous");
        wishItem.setImg(R.drawable.pen);
        items.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Saw");
        wishItem.setDescription("16\" would be perfect");
        wishItem.setCategory("Miscellaneous");
        wishItem.setImg(R.drawable.saw);
        items.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Wooden Stool");
        wishItem.setDescription("Made of any kind of wood");
        wishItem.setCategory("Furniture");
        wishItem.setImg(R.drawable.stool);
        items.add(wishItem);

        wishItem = new BarterItem();
        wishItem.setName("Tool Set");
        wishItem.setDescription("Can't find this design anywhere!");
        wishItem.setCategory("Miscellaneous");
        wishItem.setImg(R.drawable.toolbox);
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

        view.findViewById(R.id.gotoMywish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}