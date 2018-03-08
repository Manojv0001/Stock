package com.example.ashvant.stock;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ashvant on 11/26/17.
 */

public class NewsAdapter extends ArrayAdapter<NewsData> {

    private static ArrayList<NewsData> newsDetails = new ArrayList<NewsData>();

    public NewsAdapter(Context context,ArrayList<NewsData> newsList) {
        super(context,0,newsList);
        this.newsDetails = newsList;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsData currentItem = newsDetails.get(position);
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_row,parent,false);
        }
        TextView title = (TextView)listItemView.findViewById(R.id.title);
        title.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href=\""+currentItem.getLink()+"\">"+currentItem.getTitle()+"</a>";
        title.setText(Html.fromHtml(text));
        TextView author = (TextView)listItemView.findViewById(R.id.author);
        author.setText(currentItem.getAuthor());
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(currentItem.getDate());
        return listItemView;
    }
}
