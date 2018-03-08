package com.example.ashvant.stock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity{
    public ArrayAdapter<String> adapter;
    public ArrayList<String> list;
    public ArrayList<FavouriteData> favouriteData = new ArrayList<FavouriteData>();
    public FavouriteListAdapter listAdapter;
    JSONArray favArrayList = new JSONArray();
    public ProgressBar progressBar;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.yes:
                int position=0;
                FavouriteData listItem = listAdapter.getItem(info.position);
                for(int i=0;i<favouriteData.size();i++){
                    if(favouriteData.get(i).getSymbol().equals(listItem.getSymbol())){
                        position = i;
                    }
                }
                favouriteData.remove(position);
                listAdapter.notifyDataSetChanged();
                JSONArray array = new JSONArray();
                for(int i=0;i<favouriteData.size();i++){
                    JSONObject newObj = new JSONObject();
                    newObj.put("symbol",favouriteData.get(i).getSymbol());
                    newObj.put("price",favouriteData.get(i).getPrice());
                    newObj.put("change",favouriteData.get(i).getChange());
                    newObj.put("percent",favouriteData.get(i).getPercent());
                    array.add(newObj);
                }
                SharedPreferences sf = getSharedPreferences("favPrefs",0);
                SharedPreferences.Editor edit = sf.edit();
                edit.putString("favList1",array.toJSONString());
                edit.apply();
                return true;
            case R.id.no:
                // edit stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.favList) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set content view AFTER ABOVE sequence (to avoid crash)
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);
        final AutoCompleteTextView autoComplete = findViewById(R.id.autoCompleteTextView);
        final RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = getSharedPreferences("favPrefs",0);
        String favList = sharedPreferences.getString("favList1","");
        JSONArray favArrayList = new JSONArray();
        if(!favList.isEmpty()){
            try {
                favArrayList = (JSONArray) new JSONParser().parse(favList);
                for(int i=0;i<favArrayList.size();i++){
                    FavouriteData currentData = new FavouriteData();
                    currentData.setSymbol(((JSONObject)favArrayList.get(i)).get("symbol").toString());
                    currentData.setPrice(((JSONObject)favArrayList.get(i)).get("price").toString());
                    currentData.setChange(((JSONObject)favArrayList.get(i)).get("change").toString());
                    currentData.setPercent(((JSONObject)favArrayList.get(i)).get("percent").toString());
                    favouriteData.add(currentData);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        final Spinner sortBy = findViewById(R.id.sortBy);
        final Spinner orderBy = findViewById(R.id.order);
        ImageView refresh = (ImageView)findViewById(R.id.imageView);
        refresh.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                refreshFavourites();
            }
        });

        Switch sw = (Switch) findViewById(R.id.switch1);
        final Timer t = new Timer();
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    t.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refreshFavourites();
                                }
                            });

                        }
                    }, 0, 5000);
                } else {
                    t.cancel();
                    t.purge();
                }
            }
        });

        final ListView stockDetail = (ListView)findViewById(R.id.favList);

        registerForContextMenu(stockDetail);

        listAdapter = new FavouriteListAdapter(getApplicationContext(), favouriteData);
        stockDetail.setAdapter(listAdapter);


        stockDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FavouriteData listItem = listAdapter.getItem(position);
                Intent myIntent = new Intent(MainActivity.this,
                        DetailActivity.class).putExtra("symbol",listItem.getSymbol());
                startActivity(myIntent);
            }
        });

        autoComplete.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence str, int start,
                                      int before, int count) {
                String symbol = str.toString();
                new AutocompleteClass().execute(symbol);

            }
        });

        Button button = (Button) findViewById(R.id.button1);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                final AutoCompleteTextView autoComplete = findViewById(R.id.autoCompleteTextView);
                String[] symbol;
                symbol = autoComplete.getText().toString().split("-");
                if(symbol[0].trim()!=""){
                    Intent myIntent = new Intent(MainActivity.this,
                            DetailActivity.class).putExtra("symbol",symbol[0]);
                    startActivity(myIntent);
                }else{
                    Toast.makeText(getApplicationContext(),"Please Enter a valid symbol",Toast.LENGTH_SHORT).show();
                }


                // Start NewActivity.class

            }
        });

        Button clear = (Button) findViewById(R.id.button2);

        // Capture button clicks
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                autoComplete.setText("");
            }
        });

        orderBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

             public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 sortList(sortBy.getSelectedItem().toString(), orderBy.getSelectedItem().toString());
                 listAdapter.notifyDataSetChanged();
             }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sortList(sortBy.getSelectedItem().toString(),orderBy.getSelectedItem().toString());
                listAdapter.notifyDataSetChanged();
            }



            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }
    public void refreshFavourites(){
        final Spinner sortBy = findViewById(R.id.sortBy);
        final Spinner orderBy = findViewById(R.id.order);
        progressBar = findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        final SharedPreferences sharedPreferences = getSharedPreferences("favPrefs",0);
        String favList = sharedPreferences.getString("favList1","");
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = String.format("http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/refresh?symbol=" + favList);
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        String temp = response.replaceAll("^\"|\"$", "");
                        System.out.println(temp);
                        JSONArray favArrayList = new JSONArray();
                        try {
                            JSONArray array = (JSONArray) new JSONParser().parse(temp.replace("\\", ""));
                            favouriteData.clear();
                            for(int i=0;i<array.size();i++){

                                JSONObject newObj = new JSONObject();
                                FavouriteData currentData = new FavouriteData();
                                newObj.put("symbol",((JSONObject)array.get(i)).get("symbol"));
                                newObj.put("price",((JSONObject)array.get(i)).get("close"));
                                newObj.put("change",((JSONObject)array.get(i)).get("change"));
                                newObj.put("percent",((JSONObject)array.get(i)).get("percent"));
                                favArrayList.add(newObj);

                                currentData.setSymbol(((JSONObject)array.get(i)).get("symbol").toString());
                                currentData.setPrice(((JSONObject)array.get(i)).get("close").toString());
                                currentData.setChange(((JSONObject)array.get(i)).get("change").toString());
                                currentData.setPercent(((JSONObject)array.get(i)).get("percent").toString());
                                favouriteData.add(currentData);

                            }
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putString("favList1",favArrayList.toJSONString());
                            edit.apply();
                            sortList(sortBy.getSelectedItem().toString(),orderBy.getSelectedItem().toString());
                            progressBar.setVisibility(View.GONE);
                            listAdapter.notifyDataSetChanged();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(stringRequest);
    }
    public void sortList(String s, String s1) {
        if(s.equals("Default")){
            if(s1.equals("Ascending")){
                Collections.sort(favouriteData, new SymbolComparator());
            }else if(s1.equals("Descending")){
                Collections.sort(favouriteData, new SymbolDescendingComparator());

            }
        }else if(s.equals("Symbol")){
            if(s1.equals("Ascending")){
                Collections.sort(favouriteData, new SymbolComparator());
            }else if(s1.equals("Descending")){
                Collections.sort(favouriteData, new SymbolDescendingComparator());
            }

        }else if(s.equals("Price")){
            if(s1.equals("Ascending")){
                Collections.sort(favouriteData, new PriceComparator());
            }else if(s1.equals("Descending")){
                Collections.sort(favouriteData, new PriceDescendingComparator());
            }
        }else if(s.equals("Change")){
            if(s1.equals("Ascending")){
                Collections.sort(favouriteData, new ChangeComparator());
            }else if(s1.equals("Descending")){
                Collections.sort(favouriteData, new ChangeDescendingComparator());
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("favPrefs",0);
        String favList = sharedPreferences.getString("favList1","");
        JSONArray favArrayList1 = new JSONArray();
        if(!favList.isEmpty()){
            try {
                favouriteData.clear();
                favArrayList1 = (JSONArray) new JSONParser().parse(favList);
                for(int i=0;i<favArrayList1.size();i++){
                    FavouriteData currentData = new FavouriteData();
                    currentData.setSymbol(((JSONObject)favArrayList1.get(i)).get("symbol").toString());
                    currentData.setPrice(((JSONObject)favArrayList1.get(i)).get("price").toString());
                    currentData.setChange(((JSONObject)favArrayList1.get(i)).get("change").toString());
                    currentData.setPercent(((JSONObject)favArrayList1.get(i)).get("percent").toString());
                    favouriteData.add(currentData);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        listAdapter.notifyDataSetChanged();
    }

    public class AutocompleteClass extends AsyncTask<String,String,ArrayList<String>>{
        final AutoCompleteTextView autoComplete = findViewById(R.id.autoCompleteTextView);


        @Override
        protected ArrayList<String> doInBackground(String... sym) {

            final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String symbol = sym[0];
            String url = String.format("http://aselvam-stockapp-env.us-east-2.elasticbeanstalk.com/auto?symbol="+symbol);
            list = new ArrayList<String>();
            RequestFuture<String> future = RequestFuture.newFuture();
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,future,future);
            queue.add(stringRequest);
            try {
                String response = future.get(10, TimeUnit.SECONDS); // Blocks for at most 10 seconds.

                String temp = response.replaceAll("^\"|\"$", "");
                System.out.println(response);
                try {
                    JSONArray array = (JSONArray) new JSONParser().parse(temp.replace("\\",""));
                    int iterator = 0;
                    int size;
                    if(array.size()>5){
                        size = 5;
                    }else{
                        size = array.size();
                    }
                    for(int i=0;i<size;i++){
                        JSONObject obj = (JSONObject) array.get(i);
                        list.add(obj.get("Symbol").toString()+"-"+obj.get("Name").toString()+"-"+obj.get("Exchange"));

                    }
                    return list;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } catch (InterruptedException e) {
                // Exception handling
            } catch (ExecutionException e) {
                // Exception handling
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(final ArrayList<String> s) {
            runOnUiThread(new Runnable(){
                public void run(){
                    if(s!=null){
                        String[] options = new String[s.size()];
                        for(int i=0;i<s.size();i++){
                            options[i] = s.get(i);
                        }
                        adapter = new ArrayAdapter<String>
                                (getApplicationContext(), android.R.layout.select_dialog_item,options);
                        autoComplete.setAdapter(adapter);
                        autoComplete.setThreshold(1);
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }

    }

}
