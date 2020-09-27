package com.example.cruddemo.wish;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
    int wishType;
    DatabaseReference usersdatabaseReference;
    private OnWishListener monWishListener = null;

    public MyAdapter(Context appContext, ArrayList<Wish> itemModels, int typeOfWish, OnWishListener onWishListener) {
        this.appContext = appContext;
        this.itemModels = itemModels;
        this.wishType = typeOfWish;
        this.monWishListener = onWishListener;
    }

    public MyAdapter(Context appContext, ArrayList<Wish> itemModels, int typeOfWish) {
        this.appContext = appContext;
        this.itemModels = itemModels;
        this.wishType = typeOfWish;
    }

    @Override
    public int getItemViewType(int position) {
        if(wishType == 0){
            return  R.layout.a_wish;
        } else{
            return  R.layout.my_wish;
        }

    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

        return new DataHolder(view, wishType, monWishListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataHolder holder, int position) {

        holder.getpTitle().setText(itemModels.get(position).getWishName());
        holder.getpDesc().setText(itemModels.get(position).getWishDesc());
        holder.getpCateg().setText(itemModels.get(position).getWishCategory());
        Glide.with(appContext).load(itemModels.get(position).getImageURL()).into(holder.getpImageView());
        //holder.getpWishedBy().setText("Wished by: "+ itemModels.get(position).getWishOwner());
        if(wishType == 0){
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
        }


        //holder.getpImageView().setImageResource(itemModels.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public interface OnWishListener{
        void OnWishClick(int position);
    }
}
