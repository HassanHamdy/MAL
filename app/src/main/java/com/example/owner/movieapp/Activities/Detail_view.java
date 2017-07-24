package com.example.owner.movieapp.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.owner.movieapp.Data.Movie;
import com.example.owner.movieapp.Fragments.Detail_view_Fragment;
import com.example.owner.movieapp.R;


public class Detail_view extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Movie Data = (Movie) getIntent().getSerializableExtra("data");


        Detail_view_Fragment dFr = new Detail_view_Fragment();

        Bundle extras = new Bundle();
        extras.putSerializable("data", Data);
        dFr.setArguments(extras);
        FragmentManager Dfm = getFragmentManager();
        FragmentTransaction fragmentTransaction = Dfm.beginTransaction();
        fragmentTransaction.add(R.id.dContainer, dFr, "Detail");
        fragmentTransaction.commit();

    }

}

