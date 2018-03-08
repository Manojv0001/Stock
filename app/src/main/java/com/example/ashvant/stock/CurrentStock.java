package com.example.ashvant.stock;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrentStock extends Fragment {


    public static String sym, close, open, prevClose, change, percent, volume, range, timestamp;
    DecimalFormat format = new DecimalFormat("#.##");
    public ArrayList<StockDetailData> currentDetails = new ArrayList<StockDetailData>();
    public CurrentStockAdapter listAdapter;
    public String current;
    public SharedPreferences sharedPreferences;
    public ImageView favButton;
    public ProgressBar progressBar;
    public TextView failView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View fragmentView = inflater.inflate(R.layout.fragment_current_stock, container, false);
        failView = fragmentView.findViewById(R.id.failView);
        failView.setVisibility(View.GONE);
        ListView stockDetail = (ListView) fragmentView.findViewById(R.id.stockDetail);
        listAdapter = new CurrentStockAdapter(getContext(), currentDetails);
        stockDetail.setAdapter(listAdapter);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        progressBar = fragmentView.findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);


        final RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = String.format("http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/price?symbol=" + DetailActivity.symbol);
        RequestFuture<String> future = RequestFuture.newFuture();

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                            String temp = response.replaceAll("^\"|\"$", "");
                            System.out.println(temp);
                            try {
                                JSONObject object = (JSONObject) new JSONParser().parse(temp.replace("\\", ""));
                                JSONObject priceObject = (JSONObject) object.get("price");
                                if(priceObject!=null){
                                    sym = priceObject.get("symbol").toString();
                                    close = priceObject.get("close").toString();
                                    prevClose = priceObject.get("prevClose").toString();
                                    open = priceObject.get("open").toString();
                                    change = priceObject.get("change").toString();
                                    percent = priceObject.get("percent").toString();
                                    range = priceObject.get("range").toString();
                                    volume = priceObject.get("volume").toString();
                                    timestamp = priceObject.get("timestamp").toString();

                                    StockDetailData data = new StockDetailData();
                                    data.setHeader("Stock Symbol");
                                    data.setValue(sym);
                                    currentDetails.add(data);
                                    data = new StockDetailData();
                                    data.setHeader("Last Price");
                                    data.setValue(String.valueOf(format.format(Double.parseDouble(prevClose))));
                                    currentDetails.add(data);
                                    data = new StockDetailData();
                                    Double change1 = Double.valueOf(format.format(Double.parseDouble(change)));
                                    Double percent1 = Double.valueOf(format.format(Double.parseDouble(percent)));
                                    String ChangeString = change1.toString() + "(" + percent1.toString() + "%)";
                                    boolean flag = false;
                                    JSONArray favArrayList;
                                    int position = 0;

                                    sharedPreferences = getActivity().getSharedPreferences("favPrefs",Context.MODE_PRIVATE);
                                    String favList = sharedPreferences.getString("favList1","");
                                    try {
                                        favArrayList = (JSONArray) new JSONParser().parse(favList);
                                        for(int i=0;i<favArrayList.size();i++){
                                            JSONObject currentObject = (JSONObject)favArrayList.get(i);
                                            if(currentObject.get("symbol").toString().equals(DetailActivity.symbol)){
                                                flag = true;
                                                position = i;
                                            }
                                        }
                                        if(flag){
                                            favButton.setImageResource(R.drawable.filled);
                                        }else{
                                            favButton.setImageResource(R.drawable.empty);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    if (change1 > 0) {
                                        data.setHeader("Change");
                                        data.setValue(ChangeString);
                                        data.setFlag(getResources().getDrawable(R.drawable.up));
                                    } else if (change1 < 0) {
                                        data.setHeader("Change");
                                        data.setValue(ChangeString);
                                        data.setFlag(getResources().getDrawable(R.drawable.down));
                                    } else {
                                        data.setHeader("Change");
                                        data.setValue(ChangeString);
                                    }

                                    currentDetails.add(data);
                                    data = new StockDetailData();
                                    data.setHeader("Timestamp");
                                    data.setValue(timestamp);
                                    currentDetails.add(data);
                                    data = new StockDetailData();
                                    data.setHeader("Open");
                                    data.setValue(String.valueOf(format.format(Double.parseDouble(open))));
                                    currentDetails.add(data);
                                    data = new StockDetailData();
                                    data.setHeader("Close");
                                    data.setValue(String.valueOf(format.format(Double.parseDouble(close))));
                                    currentDetails.add(data);
                                    data = new StockDetailData();
                                    data.setHeader("Day's Range");
                                    data.setValue(range);
                                    currentDetails.add(data);
                                    data = new StockDetailData();
                                    data.setHeader("Volume");
                                    data.setValue(volume);
                                    currentDetails.add(data);
                                    System.out.println(currentDetails.get(0).getValue());
                                    progressBar.setVisibility(View.GONE);

                                    listAdapter.notifyDataSetChanged();
                                }else{
                                    System.out.println("failed to load DAta");
                                    failView.setVisibility(View.VISIBLE);
                                    failView.setText("Failed to load Data");
                                    progressBar.setVisibility(View.GONE);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                System.out.println("fail1");
                                progressBar.setVisibility(View.GONE);
                                failView.setVisibility(View.VISIBLE);
                                failView.setText("Failed to load Data");
                            }

                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        failView.setVisibility(View.VISIBLE);
                        failView.setText("Failed to load Data");
                    }
                });

        queue.add(stringRequest);

        ImageView facebook = fragmentView.findViewById(R.id.fbButton);
        facebook.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();

            }
        });



        final WebView webView = fragmentView.findViewById(R.id.indicatorView);

        webView.loadUrl("file:///android_asset/indicator.html");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:getAndSetData(\""+DetailActivity.symbol+"\")");
                current = "Price";
            }
        });
        final Button button = (Button)fragmentView.findViewById(R.id.button);
        button.setEnabled(false);
        final Spinner mySpinner = (Spinner)fragmentView.findViewById(R.id.spinner);
        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                String text = mySpinner.getSelectedItem().toString();
                if(text.equals("SMA")){
                    webView.loadUrl("javascript:displaySMAChart()");
                    current="SMA";
                }else if(text.equals("EMA")){
                    webView.loadUrl("javascript:displayEMAChart()");
                    current="EMA";
                }else if(text.equals("BBANDS")){
                    webView.loadUrl("javascript:displayBBANDSChart()");
                    current="BBANDS";
                }else if(text.equals("RSI")){
                    webView.loadUrl("javascript:displayRSIChart()");
                    current="RSI";
                }else if(text.equals("ADX")){
                    webView.loadUrl("javascript:displayADXChart()");
                    current="ADX";
                }else if(text.equals("MACD")){
                    webView.loadUrl("javascript:displayMACDChart()");
                    current="MACD";
                }else if(text.equals("STOCH")){
                    webView.loadUrl("javascript:displaySTOCHChart()");
                    current="STOCH";
                }else if(text.equals("CCI")){
                    webView.loadUrl("javascript:displayCCIChart()");
                    current="CCI";
                }else if(text.equals("Price")){
                    webView.loadUrl("javascript:displayPriceChart()");
                    current="Price";
                }
                button.setEnabled(false);
            }

        });
        favButton = (ImageView)fragmentView.findViewById(R.id.favButton);
        favButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                boolean flag = false;
                JSONArray newArray = new JSONArray();
                JSONArray favArrayList = new JSONArray();
                int position = 0;
                sharedPreferences = getActivity().getSharedPreferences("favPrefs",Context.MODE_PRIVATE);
                String favList = sharedPreferences.getString("favList1","");
                Toast.makeText(getContext(),favList,Toast.LENGTH_SHORT).show();
                try {
                    if(!favList.isEmpty()){
                        favArrayList = (JSONArray) new JSONParser().parse(favList);
                        for(int i=0;i<favArrayList.size();i++){
                            JSONObject currentObject = (JSONObject)favArrayList.get(i);
                            if(currentObject.get("symbol").toString().equals(DetailActivity.symbol)){
                                flag = true;
                                position = i;
                            }
                        }
                    }

                    if(flag){
                        for(int i=0;i<favArrayList.size();i++){
                            if(i!=position){
                                newArray.add(favArrayList.get(i));
                            }
                        }
                        //Toast.makeText(getContext(),position,Toast.LENGTH_SHORT).show();
                        favButton.setImageResource(R.drawable.empty);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("favList1",newArray.toJSONString());
                        edit.apply();
                    }else{
                        JSONObject newObj = new JSONObject();
                        newObj.put("symbol",sym);
                        newObj.put("price",currentDetails.get(5).getValue());
                        newObj.put("change",change);
                        newObj.put("percent",percent);
                        favArrayList.add(newObj);
                        favButton.setImageResource(R.drawable.filled);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.putString("favList1",favArrayList.toJSONString());

                        edit.apply();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mySpinner.getSelectedItem().toString().equals(current)){
                    button.setEnabled(false);
                }else{
                    button.setEnabled(true);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        return fragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



}
