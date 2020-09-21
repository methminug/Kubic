package com.example.cruddemo.wish;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<DataHolder> {

    Context appContext;
    ArrayList<Wish> itemModels;
    DatabaseReference usersdatabaseReference;

    public MyAdapter(Context appContext, ArrayList<Wish> itemModels) {
        this.appContext = appContext;
        this.itemModels = itemModels;
    }

    @Override
    public int getItemViewType(int position) {
        return  R.layout.a_wish;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataHolder holder, int position) {

        holder.getpTitle().setText(itemModels.get(position).getWishName());
        holder.getpDesc().setText(itemModels.get(position).getWishDesc());
        holder.getpCateg().setText(itemModels.get(position).getWishCategory());
        //holder.getpWishedBy().setText("Wished by: "+ itemModels.get(position).getWishOwner());
        String ownerID = itemModels.get(position).getWishOwner();
        usersdatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(ownerID);
        usersdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uname = snapshot.child("Username").getValue().toString();
                if(uname != null){
                    Log.w("This Username", uname);
                    holder.getpWishedBy().setText("Wished by: "+  uname );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //holder.getpImageView().setImageResource(itemModels.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }
}
