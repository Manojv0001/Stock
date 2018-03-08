package com.example.ashvant.stock;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import org.json.simple.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by ashvant on 11/28/17.
 */

public class FavouriteListAdapter extends ArrayAdapter <FavouriteData> {
    private static ArrayList<FavouriteData> currentDetails = new ArrayList<FavouriteData>();
    DecimalFormat format = new DecimalFormat("#.##");

    public FavouriteListAdapter(Context context, ArrayList<FavouriteData> resource) {
        super(context,0,resource);
        this.currentDetails = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FavouriteData currentItem = currentDetails.get(position);
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.fav_list_row,parent,false);
        }
        TextView symbol = (TextView)listItemView.findViewById(R.id.textView3);
        symbol.setText(currentItem.getSymbol().toString());
        String price = currentItem.getPrice().toString();
        TextView value = (TextView)listItemView.findViewById(R.id.textView4);
        value.setText(String.valueOf(format.format(Double.parseDouble(price))));
        String changeplus = currentItem.getChange() + "(" + currentItem.getPercent() + ")";
        if(Double.parseDouble(currentItem.getChange().toString())<0){
            TextView change = (TextView)listItemView.findViewById(R.id.textView5);
            change.setText(changeplus);
            change.setTextColor(Color.parseColor("#db0a18"));
        }else{
            TextView change = (TextView)listItemView.findViewById(R.id.textView5);
            change.setText(changeplus);
            change.setTextColor(Color.parseColor("#42f465"));
        }


        return listItemView;
    }
}
