package com.example.owner.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Owner on 11-Oct-16.
 */

public class Detail_view extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Movie data = (Movie) getIntent().getSerializableExtra("data");

        TextView title = (TextView) findViewById(R.id.textView_title);
        title.setText(data.getTitle());

        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(data.getImage());

        RatingBar Rate = (RatingBar) findViewById(R.id.rating_bar);
        Rate.setRating((float) data.getRate());

        TextView description = (TextView) findViewById(R.id.textView_description);
        description.setText(data.getDescription());
    }
}
