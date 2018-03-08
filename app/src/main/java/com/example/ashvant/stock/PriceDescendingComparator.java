package com.example.ashvant.stock;

import java.util.Comparator;

/**
 * Created by ashvant on 11/28/17.
 */

public class PriceDescendingComparator implements Comparator<FavouriteData> {
    @Override
    public int compare(FavouriteData o, FavouriteData t1) {
        if(Double.parseDouble(o.getPrice())>Double.parseDouble(t1.getPrice())){
            return -1;
        }else if(Double.parseDouble(o.getPrice())<Double.parseDouble(t1.getPrice())){
            return 1;
        }else{
            return 0;
        }
    }
}
