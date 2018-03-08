package com.example.ashvant.stock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashvant on 11/26/17.
 */

public class CurrentStockAdapter extends ArrayAdapter<StockDetailData> {

    private static ArrayList<StockDetailData> currentDetails = new ArrayList<StockDetailData>();


    public CurrentStockAdapter(Context context, ArrayList<StockDetailData> resource) {
        super(context,0,resource);
        this.currentDetails = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StockDetailData currentItem = currentDetails.get(position);
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.stock_detail_list_row,parent,false);
        }
        TextView header = (TextView)listItemView.findViewById(R.id.detail);
        header.setText(currentItem.getHeader());
        TextView value = (TextView)listItemView.findViewById(R.id.value);
        value.setText(currentItem.getValue());
        ImageView upDown = (ImageView)listItemView.findViewById(R.id.imageView);
        upDown.setImageDrawable(currentItem.getFlag());
        return listItemView;
    }
}