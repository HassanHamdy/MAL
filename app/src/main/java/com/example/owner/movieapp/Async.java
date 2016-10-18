package com.example.owner.movieapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Owner on 18-Oct-16.
 */

public class Async extends AsyncTask<String, String, Void> {
    @Override
    protected Void doInBackground(String... params) {
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
            Log.d("Hassan",Jsonstr);

            //send Data to send it to onProgressUpdate method
            //publishProgress(Jsonstr);

        } catch (MalformedURLException e) {
            e.printStackTrace();//catch to exception of (URL url = new URL(params[0]))
        } catch (IOException e) {
            e.printStackTrace(); //catch to exception of (HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();)
        }
        return null;
    }
}
