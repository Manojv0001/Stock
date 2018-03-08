package com.example.ashvant.stock;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class News extends android.support.v4.app.Fragment {

    public ArrayList<NewsData> newsList = new ArrayList<NewsData>();
    NewsAdapter listAdapter;
    ProgressBar progressBar;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_news, container, false);
        textView = fragmentView.findViewById(R.id.textView11);
        textView.setVisibility(View.INVISIBLE);
        progressBar = fragmentView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        ListView newsListView = (ListView) fragmentView.findViewById(R.id.newsListView);
        listAdapter = new NewsAdapter(getContext(),newsList);
        newsListView.setAdapter(listAdapter);
        new getNewsData().execute(DetailActivity.symbol);

        return fragmentView;

    }

    public class getNewsData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... symbol) {
            final RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = String.format("http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/news?symbol=" + symbol[0].toString());

            RequestFuture<String> future = RequestFuture.newFuture();
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future);
            queue.add(stringRequest);
            try {
                String response = future.get(10, TimeUnit.SECONDS);

                String temp = response.replaceAll("^\"|\"$", "");
                System.out.println(temp.replace("\\", ""));
                JSONObject news = (JSONObject) new JSONParser().parse(temp.replace("\\", ""));
                JSONArray newsArray = (JSONArray) news.get("news");
                for(int i=0;i<newsArray.size();i++){
                    NewsData data = new NewsData();
                    JSONObject newsItem = (JSONObject) newsArray.get(i);
                    data.setTitle(newsItem.get("title").toString());
                    data.setAuthor(newsItem.get("author").toString());
                    data.setDate(newsItem.get("pub_date").toString());
                    data.setLink(newsItem.get("link").toString());
                    newsList.add(data);
                }
                NewsData data = new NewsData();
                data.setTitle("");
                data.setAuthor("");
                data.setDate("");
                data.setLink("");
                newsList.add(data);
                return "set";
            } catch (InterruptedException e) {
                // Exception handling
                return null;
            } catch (ExecutionException e) {
                // Exception handling
                return null;
            } catch (TimeoutException e) {
                e.printStackTrace();
                return null;
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            if (s==null){
                textView.setVisibility(View.VISIBLE);
                textView.setText("Failed to load news Data");
            }

            listAdapter.notifyDataSetChanged();
        }
    }

}
