package com.example.owner.movieapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class youtubeAsync extends AsyncTask<String, String, ArrayList<String>> {

    public interface onResponse {
        public void onSuccess(ArrayList<String> data);
    }

    youtubeAsync.onResponse listen;
    private Context context;
    ArrayList<String> Data = new ArrayList<String>();
    int identify_Json;

    public youtubeAsync(Context c, youtubeAsync.onResponse listener) {
        context = c;
        this.listen = listener;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        String Jsonstr = "";

        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            //convert From JSON to String

            BufferedReader bureader = new BufferedReader(new InputStreamReader(in));
            String line;
            try {
                while ((line = bureader.readLine()) != null) {
                    Jsonstr += line;
                }
                in.close();
            } catch (Exception ex) {
            }
            //JSON parsing
            JSONObject JsonRootObject = null;
            try {
                JsonRootObject = new JSONObject(Jsonstr);
                JSONArray JsonArr = JsonRootObject.getJSONArray("results");
                for (int i = 0; i < JsonArr.length(); i++) {
                    JSONObject JSONobject = JsonArr.getJSONObject(i);
                    String Key = JSONobject.getString("key");
                    Data.add("https://www.youtube.com/watch?v=" + Key);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //send Data to send it to onProgressUpdate method
            //publishProgress(Jsonstr);

        } catch (MalformedURLException e) {
            e.printStackTrace();//catch to exception of (URL url = new URL(params[0]))
        } catch (IOException e) {
            e.printStackTrace(); //catch to exception of (HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();)
        }
        return Data;
    }

    @Override
    protected void onPostExecute(final ArrayList<String> keys) {
        listen.onSuccess(keys);
    }
}
