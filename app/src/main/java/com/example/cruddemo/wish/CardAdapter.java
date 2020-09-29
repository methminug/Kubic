package com.example.cruddemo.wish;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cruddemo.R;
import com.huxq17.swipecardsview.BaseCardAdapter;

import java.util.ArrayList;

public class CardAdapter extends BaseCardAdapter {

    Context appContext;
    ArrayList<BarterItem> itemModels;

    public CardAdapter(Context appContext, ArrayList<BarterItem> itemModels) {
        this.appContext = appContext;
        this.itemModels = itemModels;
    }

    @Override
    public int getCount() {
        return itemModels.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }

    @Override
    public void onBindData(int position, View cardview) {

        if(itemModels == null || itemModels.size() == 0){
            return;
        }
        ImageView itemImgv = (ImageView)cardview.findViewById(R.id.itemImg);
        TextView itemTitle = (TextView)cardview.findViewById(R.id.ititle);
        TextView itemDesc = (TextView)cardview.findViewById(R.id.idesc);
        TextView itemExchange = (TextView)cardview.findViewById(R.id.iexchange);
        BarterItem barterItem = itemModels.get(position);
        itemTitle.setText(barterItem.getName());
        itemDesc.setText(barterItem.getDescription());
        itemExchange.setText(barterItem.getCategory());

        itemImgv.setImageResource(barterItem.getImg());

    }
}
