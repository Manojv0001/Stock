package com.example.ashvant.stock;

import android.graphics.drawable.Drawable;

/**
 * Created by ashvant on 11/26/17.
 */

public class StockDetailData {
    private String Header = "";
    private String value = "";
    private Drawable flag;

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Drawable getFlag() {
        return flag;
    }

    public void setFlag(Drawable flag) {
        this.flag = flag;
    }
}
