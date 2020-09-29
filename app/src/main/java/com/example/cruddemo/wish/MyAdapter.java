package com.example.cruddemo.wish;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<DataHolder> {

    Context appContext;
    ArrayList<BarterItem> itemModels;

    public MyAdapter(Context appContext, ArrayList<BarterItem> itemModels) {
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
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {

        holder.getpTitle().setText(itemModels.get(position).getName());
        holder.getpDesc().setText(itemModels.get(position).getDescription());
        holder.getpCateg().setText(itemModels.get(position).getCategory());
        holder.getpImageView().setImageResource(itemModels.get(position).getImg());

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }
}
