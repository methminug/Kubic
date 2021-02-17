package com.example.cruddemo.wish;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemListsAdapter extends RecyclerView.Adapter<ItemListsAdapter.ItemListsViewHolder> {

    private List<DisplayItem> itemList;
    DataBaseServices dataBaseServices = new DataBaseServices();

    ItemListsAdapter(List<DisplayItem> itemslists){
        this.itemList = itemslists;
    }

    @NonNull
    @Override
    public ItemListsAdapter.ItemListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("ItemListsAdapter","START");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paired_item, parent, false);
        return new ItemListsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemListsAdapter.ItemListsViewHolder holder, int position) {

//        Log.i("Over","HERE 2");
//        holder.category.setText(maindisplayItem.getCategory());
//        holder.description.setText(maindisplayItem.getDescription());
//        holder.title.setText(maindisplayItem.getTitle());
        Log.i("part 7","here");
        holder.category.setText(itemList.get(position).getCategory());
        holder.description.setText(itemList.get(position).getDescription());
        holder.title.setText(itemList.get(position).getTitle());
        Glide.with(holder.image.getContext()).load(itemList.get(position).getImage()).into(holder.image);

    }

    @Subscribe
    public void receiveMessage(DisplayItem pdisplayItem){
        Log.i("Over","HERE 2");
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        BusStation.getBus().register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        BusStation.getBus().unregister(this);
    }

    @Override
    public int getItemCount() {
        Log.i("Length itemlists", String.valueOf(itemList.size()));

        return itemList.size();
    }

    class ItemListsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView category;
        ImageView image;

        ItemListsViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.anItemName);
            description = itemView.findViewById(R.id.anItemDesc);
            category = itemView.findViewById(R.id.anItemCategory);
            image = itemView.findViewById(R.id.anItemImage);
        }
    }

}
