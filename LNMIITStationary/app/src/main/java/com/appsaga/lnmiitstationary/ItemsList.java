package com.appsaga.lnmiitstationary;

import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.Attributes;

public class ItemsList extends AppCompatActivity implements android.widget.CompoundButton.OnCheckedChangeListener {

    ListView listView;
    ArrayList<Items> itemList = new ArrayList<>();;
    FloatingActionButton AddItem;
    FloatingActionButton Order;
    String[] nameArray;
    Set<String> names = new TreeSet<>();
    ItemsAdapter itemsAdapter;
    ArrayList<String> items;

    private static final String ITEM_LIST_URL =
            "http://stationeryapi.pythonanywhere.com/item/?format=json";

    public static final String LOG_TAG = ItemsList.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        AddItem = findViewById(R.id.addItem);
        Order = findViewById(R.id.order);

        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(ItemsList.this, com.appsaga.lnmiitstationary.AddItem.class);
                startActivity(intent1);
            }
        });

        //itemList.add(new Items(1, "pen", false, 20, 2, 2, false));
        //itemList.add(new Items(1, "pencil", false, 20, 2, 2, true));

        itemsAdapter = new ItemsAdapter(this, itemList);

        listView = findViewById(R.id.list);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        new getItems().execute();

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ItemsList.this,"Clicked on",Toast.LENGTH_SHORT).show();
            }
        });*/

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(ItemsList.this, com.appsaga.lnmiitstationary.OrderSummary.class);

                //String name = getName();
                List<String> nameList = Arrays.asList(nameArray);

                items = new ArrayList<>(nameList);

                //if(!name.equals(" ")) {
                    intent2.putExtra("items", items);
                    startActivity(intent2);
                //}
                /*else
                {
                    intent2.putExtra("name"," List is empty. Please place your order");
                    startActivity(intent2);
               }*/
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        int pos = listView.getPositionForView(buttonView);

        if (pos != ListView.INVALID_POSITION) {

            Items item = itemList.get(pos);
            item.setSelected(isChecked);

            if (item.isSelected()) {

                names.add(item.getName());
            } else {
                names.remove(item.getName());
            }

            nameArray = names.toArray(new String[names.size()]);

        }
    }

   /* public String getName(){

        String name=" ";

        if(nameArray.length!=0) {
            for (int i = 0; i < nameArray.length; i++) {

                name = name + " " + nameArray[i] + " ";
            }
            Log.e("Entered log","I have entered");
        }

        return  name;
    }*/

    public class getItems extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voidArgs) {
            // Create URL object
            URL url = createUrl(ITEM_LIST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            extractFeatureFromJson(jsonResponse);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            listView.setAdapter(itemsAdapter);
        }
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private void extractFeatureFromJson(String itemsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(itemsJSON)) {
            return ;
        }

        try {
            JSONArray featureArray = new JSONArray(itemsJSON);

            // If there are results in the features array
            for(int i=0;i<featureArray.length();i++) {
                    // Extract out the first feature (which is an earthquake)
                    JSONObject item = featureArray.getJSONObject(i);
                    int ID = item.getInt("PID");
                    String name =item.getString("Name");
                    boolean perishable = item.getBoolean("Perishable");
                    int qty = item.getInt("Qty");
                    int onHold = item.getInt("OnHold");
                   // int minOty = item.getInt("MinOty");
                    boolean orderReq = item.getBoolean("OrderReq");

                    itemList.add(new Items(ID,name,perishable,qty,onHold,0,false));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return ;
    }
}
