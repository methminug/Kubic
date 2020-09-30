package com.example.cruddemo.wish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.cruddemo.R;

public class DeleteWish extends DialogFragment {

    private String wishName;
    private Context context;

    public DeleteWish(String deletewishName, Context pcontext) {
        this.wishName=deletewishName;
        context = pcontext;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view  = LayoutInflater.from(context).inflate(R.layout.deletewish_dialog, null);

        builder.setView(view);
        ((TextView)view.findViewById(R.id.deletewishNameDisp)).setText(this.wishName);

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.deleteWish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Toast.makeText(context,"Wish deleted successfully",Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null){
            // ???????
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }

        alertDialog.show();
    }
}
