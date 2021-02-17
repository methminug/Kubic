package com.example.cruddemo.wish;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cruddemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.otto.Subscribe;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ItemPairsAdapter extends RecyclerView.Adapter<ItemPairsAdapter.ItemPairsViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private RecyclerView.RecycledViewPool viewPool2 = new RecyclerView.RecycledViewPool();
    private List<List<List<String>>> pairsList;
    private DataBaseServices dataBaseServices = new DataBaseServices();

    ItemPairsAdapter(List<List<List<String>>> itemList) {
        this.pairsList = itemList;
    }


    @NonNull
    @Override
    public ItemPairsAdapter.ItemPairsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("ItemPairsAdapter","START");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_matched, parent, false);
        return new ItemPairsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemPairsAdapter.ItemPairsViewHolder holder, int position) {
        List<List<String>> item = pairsList.get(position);
        String itemID = item.get(0).get(0);
        Log.i("part 1","here");
        dataBaseServices.getItemsRef().child(itemID).child("offeredBy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ownerID = snapshot.getValue().toString();

                if(ownerID != null){
                    Log.i("Owner id for itemset",ownerID);
                    dataBaseServices.getAUser(ownerID, holder.ownerName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.i("part 2","here");
        final List<DisplayItem> swiped = new ArrayList<DisplayItem>();
        final List<DisplayItem> wants = new ArrayList<DisplayItem>();
        for(String str : item.get(0)){
            Log.i("part 3","here");
            dataBaseServices.getItemsRef().child(str).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String title = snapshot.child("name").getValue().toString();
                    String desc = snapshot.child("description").getValue().toString();
                    String categ = snapshot.child("category").getValue().toString();
                    String img = snapshot.child("iImage").getValue().toString();
                    DisplayItem displayIteml = new DisplayItem(title, desc, categ, img);
                    BusStation.getBus().post(displayIteml);
                    swiped.add(displayIteml);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            Log.i("part 4","here");
        }
        for(String str : item.get(1)){
            Log.i("part 5","here");
            dataBaseServices.getItemsRef().child(str).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String title = snapshot.child("name").getValue().toString();
                    String desc = snapshot.child("description").getValue().toString();
                    String categ = snapshot.child("category").getValue().toString();
                    String img = snapshot.child("iImage").getValue().toString();
                    DisplayItem displayIteml = new DisplayItem(title, desc, categ, img);
                    BusStation.getBus().post(displayIteml);
                    wants.add(displayIteml);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            Log.i("part 6","here");
        }

        // Create layout manager with initial prefetch item count
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyvlevuser.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(
                holder.recyvlevowner.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(item.size());
        layoutManager2.setInitialPrefetchItemCount(item.size());


        // Create sub item view adapter
        ItemListsAdapter subItemAdapter = new ItemListsAdapter(wants);
        ItemListsAdapter subItemAdapter2 = new ItemListsAdapter(swiped);

        holder.recyvlevuser.setLayoutManager(layoutManager);
        holder.recyvlevuser.setAdapter(subItemAdapter);
        holder.recyvlevuser.setRecycledViewPool(viewPool);

        holder.recyvlevowner.setLayoutManager(layoutManager2);
        holder.recyvlevowner.setAdapter(subItemAdapter2);
        holder.recyvlevowner.setRecycledViewPool(viewPool2);
    }

    @Override
    public int getItemCount() {
        Log.i("Length Pairs", String.valueOf(pairsList.size()));
        Log.i("Pairs content", Arrays.deepToString(pairsList.toArray()));
        return pairsList.size();
    }

    class ItemPairsViewHolder extends RecyclerView.ViewHolder{
        private TextView ownerName;
        private RecyclerView recyvlevuser;
        private RecyclerView recyvlevowner;

        public ItemPairsViewHolder(@NonNull View itemView) {
            super(itemView);
            ownerName = itemView.findViewById(R.id.ownerSwipedOnLabel);
            recyvlevuser = itemView.findViewById(R.id.itemOfUser);
            recyvlevowner = itemView.findViewById(R.id.swipedItemsOfUser);
        }
    }
}
