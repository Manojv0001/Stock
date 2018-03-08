package com.example.ashvant.stock;

import java.util.Comparator;

/**
 * Created by ashvant on 11/28/17.
 */

public class SymbolComparator implements Comparator<FavouriteData> {
    @Override
    public int compare(FavouriteData o, FavouriteData t1) {
        return o.getSymbol().compareTo(t1.getSymbol());
    }
}
