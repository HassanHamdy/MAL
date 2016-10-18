package com.example.owner.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data = new ArrayList<Movie>();
        Data.add(new Movie(R.drawable.koala, "koala", "koala", 2.5));
        Data.add(new Movie(R.drawable.koala, "koala", "koala", 3));
        Data.add(new Movie(R.drawable.koala, "koala", "koala", 3));
        Data.add(new Movie(R.drawable.koala, "koala", "koala", 4));
        Data.add(new Movie(R.drawable.koala, "koala", "koala", 5));


        /*String JSONstr = "{\"info\":[{\"id\" : \" 1 \" , \"name\" : \" Desert sand\" , \"Description\" : \"Desert\" , \"rate\" : 1  }, " +
                "{\"id\" : \" 2 \" , \"name\" : \" hydrangeas\" , \"Description\" : \"hydrangeas\" , \"rate\" : 1.5  } ," +
                " {\"id\" : \" 3 \" , \"name\" : \" jellyfish\" , \"Description\" : \"jellyfish\" , \"rate\" : 2  }]}";

        try {
            JSONObject RootObject = new JSONObject(JSONstr);
            JSONArray JSONarr = RootObject.getJSONArray("info");
            for(int i = 0; i < JSONarr.length(); i++) {
                JSONObject JSONobject = JSONarr.getJSONObject(i);
                int id = Integer.parseInt(JSONobject.getString("id"));
                switch (id){
                    case 1:
                        id = R.drawable.desert;
                        break;
                    case 2:
                        id = R.drawable.hydrangeas;
                        break;
                    case 3:
                        id = R.drawable.jellyfish;
                        break;
                }
                Log.d("Hassan",String.valueOf(id));
                String name = JSONobject.getString("name");
                String Description = JSONobject.getString("Description");
                double rate = JSONobject.getDouble("rate");
                Data.add(new Movie(id,name,Description,rate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        /*Movie[] data = {
                new Movie(R.drawable.koala, "koala", "koala", 2.5),
                new Movie(R.drawable.koala, "koala", "koala", 3),
                new Movie(R.drawable.koala, "koala", "koala", 3),
                new Movie(R.drawable.koala, "koala", "koala", 4),
                new Movie(R.drawable.koala, "koala", "koala", 5)
        };*/

        try {
            String myUrl = "";
            new Async().execute(myUrl);
        }catch (Exception ex){}

        GridView gridView = (GridView) findViewById(R.id.Grid_View);
        gridView.setAdapter(new AdapterClass(this,Data));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                startActivity(new Intent(MainActivity.this, Detail_view.class).putExtra("data", Data.get(position)));
            }
        });
    }
}
