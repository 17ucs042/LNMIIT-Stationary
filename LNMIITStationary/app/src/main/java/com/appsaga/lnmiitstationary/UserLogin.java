package com.appsaga.lnmiitstationary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class UserLogin extends AppCompatActivity {

    TextView signup;
    EditText email;
    EditText id;
    String user_email;
    int user_id;
    Button sign_in;

    HashMap<Integer,String> faculties = new HashMap<>();

    public static final String LOG_TAG = UserLogin.class.getSimpleName();

    private static final String FACULTY_LIST_URL =
            "http://stationeryapi.pythonanywhere.com/faculty/?format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        signup = findViewById(R.id.user_sign_up);
        sign_in = findViewById(R.id.user_email_sign_in_button);

        signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserLogin.this,com.appsaga.lnmiitstationary.UserSignUp.class);
                startActivity(intent);
            }
        });

        email = findViewById(R.id.user_email);
        id = findViewById(R.id.user_id);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!email.getText().equals(null)) {
                    user_email = email.getText().toString().trim();
                }
                else{
                    Toast.makeText(UserLogin.this,"Please enter your email",Toast.LENGTH_SHORT).show();
                }
                if(!id.getText().equals(null)) {
                    user_id = Integer.parseInt(id.getText().toString().trim());
                }
                else{
                    Toast.makeText(UserLogin.this,"Please enter your id",Toast.LENGTH_SHORT).show();
                }
                new getNames().execute();
            }
        });
    }

    public class getNames extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voidArgs) {
            // Create URL object
            URL url = createUrl(FACULTY_LIST_URL);

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

            if(faculties.containsKey(user_id)) {

                if(faculties.containsValue(user_email)){

                    Intent intent = new Intent(UserLogin.this,com.appsaga.lnmiitstationary.ItemsList.class);
                    Toast.makeText(UserLogin.this,"Please Wait",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(UserLogin.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                }
            }
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

    private void extractFeatureFromJson(String FacultyJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(FacultyJSON)) {
            return ;
        }

        try {
            JSONArray featureArray = new JSONArray(FacultyJSON);

            // If there are results in the features array
            for(int i=0;i<featureArray.length();i++) {
                // Extract out the first feature (which is an earthquake)
                JSONObject faculty = featureArray.getJSONObject(i);
                int ID = faculty.getInt("FID");
                String name =faculty.getString("Name");
                String email = faculty.getString("Email");

                faculties.put(ID,email);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return ;
    }
}

