package com.example.cruddemo.wish;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cruddemo.R;

public class DataHolder extends RecyclerView.ViewHolder {

    ImageView pImageView;
    TextView pDesc, pTitle, pCateg;

    public DataHolder(@NonNull View itemView) {
        super(itemView);

        this.pImageView = itemView.findViewById(R.id.wishImage);
        this.pTitle = itemView.findViewById(R.id.wishItemName);
        this.pDesc = itemView.findViewById(R.id.wishItemDesc);
        this.pCateg = itemView.findViewById(R.id.wishCategory);
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
}
