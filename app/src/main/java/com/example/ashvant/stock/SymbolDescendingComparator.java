package com.example.ashvant.stock;

import java.util.Comparator;

/**
 * Created by ashvant on 11/28/17.
 */

public class SymbolDescendingComparator implements Comparator<FavouriteData> {
    @Override
    public int compare(FavouriteData o, FavouriteData t1) {
        return t1.getSymbol().compareTo(o.getSymbol());
    }
}
