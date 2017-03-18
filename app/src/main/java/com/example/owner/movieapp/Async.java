package com.example.owner.movieapp;

import android.content.Context;
import android.os.AsyncTask;

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


public class Async extends AsyncTask<String, String, ArrayList<Movie>> {

    public interface onResponse {
        public void onSuccess(ArrayList<Movie> data);
    }

    onResponse listen;
    private Context context;
    ArrayList<Movie> Data = new ArrayList<Movie>();

    public Async(Context c, onResponse listener) {
        context = c;
        this.listen = listener;
    }

    public Async(Context c, int identifier) {
        context = c;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
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
                    String imgPath = JSONobject.getString("poster_path");
                    imgPath = "https://image.tmdb.org/t/p/w780" + imgPath;
                    String BackimgPath = JSONobject.getString("backdrop_path");
                    BackimgPath = "https://image.tmdb.org/t/p/w780" + BackimgPath;
                    String description = JSONobject.getString("overview");
                    String Title = JSONobject.getString("title");
                    Double rate = JSONobject.getDouble("vote_average");
                    String Date = JSONobject.getString("release_date");
                    int id = JSONobject.getInt("id");
                    Data.add(new Movie(imgPath, description, Title, rate, Date, BackimgPath, id));
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
    protected void onPostExecute(final ArrayList<Movie> movies) {
        listen.onSuccess(movies);
    }

}
