package com.example.cruddemo.wish;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;

public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView pImageView;
    TextView pDesc, pTitle, pCateg, pWishedBy;
    MyAdapter.OnWishListener onWishListener;

    public DataHolder(@NonNull View itemView, int wishType, MyAdapter.OnWishListener onWishListener) {
        super(itemView);
        this.pImageView = itemView.findViewById(R.id.wishImage);
        this.pTitle = itemView.findViewById(R.id.wishItemName);
        this.pDesc = itemView.findViewById(R.id.wishItemDesc);
        this.pCateg = itemView.findViewById(R.id.wishCategory);
        this.onWishListener = onWishListener;
        if (wishType == 0){
            this.pWishedBy = itemView.findViewById(R.id.username);
        }

        //SET TO BUTTON
        itemView.setOnClickListener(this);

    }

    public ImageView getpImageView() {
        return pImageView;
    }

    public TextView getpDesc() {
        return pDesc;
    }

    public TextView getpTitle() {
        return pTitle;
    }

    public TextView getpCateg() {
        return pCateg;
    }

    public TextView getpWishedBy() {
        return pWishedBy;
    }

    @Override
    public void onClick(View view) {
        if(onWishListener!=null){
            onWishListener.OnWishClick(getAdapterPosition());
        }
    }
}
