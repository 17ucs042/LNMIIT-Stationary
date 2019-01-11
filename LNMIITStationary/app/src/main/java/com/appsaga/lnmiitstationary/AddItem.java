package com.appsaga.lnmiitstationary;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddItem extends AppCompatActivity {

    EditText itemName;
    EditText itemType;
    Button add;

    private static final String USGS_REQUEST_URL =
            "http://stationeryapi.pythonanywhere.com/item/?format=json";

    public static final String LOG_TAG = AddItem.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName=findViewById(R.id.item_name);
        itemType=findViewById(R.id.item_type);
        add=findViewById(R.id.add);

       add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject postData = new JSONObject();
                try {
                    postData.put("PID", 3);
                    postData.put("Name", itemName.getText().toString());
                    postData.put("Perishable", false);
                    postData.put("Oty", 20);
                    postData.put("OnHold", 0);
                    postData.put("MinQty", 0);
                    postData.put("OrderReq", false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (postData.length() > 0) {
                    new SendJsonDataToServer().execute(String.valueOf(postData));
                }
            }
        });

        //Intent intent = new Intent(AddItem.this,com.appsaga.lnmiitstationary.ItemsList.class);
        //startActivity(intent);
    }

    class SendJsonDataToServer extends AsyncTask <String,String,String>{

        @Override
        protected String doInBackground(String... params) {

            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(USGS_REQUEST_URL);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
// json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
//input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
//response data
                Log.i(LOG_TAG,JsonResponse);
                try {
//send to post execute
                    return JsonResponse;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;



            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {

            Intent intent = new Intent(AddItem.this,com.appsaga.lnmiitstationary.ItemsList.class);
            startActivity(intent);
        }

    }
}
