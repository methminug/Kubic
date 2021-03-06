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

    private Wish deletewish;
    private int position;
    private SecondFragment fragmentRef;
    private Context context;
    private DataBaseServices dataBaseServices = new DataBaseServices();

    public DeleteWish(Wish wish, Context pcontext, SecondFragment secondFragment, int position) {
        this.deletewish = wish;
        this.position = position;
        this.fragmentRef = secondFragment;
        this.context = pcontext;
    }

    public void showDeleteDialog() {
        final int[] output = new int[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view  = LayoutInflater.from(context).inflate(R.layout.deletewish_dialog, null);

        builder.setView(view);
        ((TextView)view.findViewById(R.id.deletewishNameDisp)).setText(deletewish.getWishName());

        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.deleteWish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

                dataBaseServices.deleteWish(deletewish, context);
                fragmentRef.wishDeleted(position);

            }
        });

        view.findViewById(R.id.dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        if(alertDialog.getWindow()!=null){
            // Removes black background of pop up dialog
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }

        alertDialog.show();
    }
}
