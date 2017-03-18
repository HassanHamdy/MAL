package com.example.owner.movieapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;


public class Detail_view extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Detail_view_Fragment dFr = new Detail_view_Fragment();
        FragmentManager Dfm = getFragmentManager();
        FragmentTransaction fragmentTransaction = Dfm.beginTransaction();
        fragmentTransaction.add(R.id.dContainer, dFr, "Detail");
        fragmentTransaction.commit();

    }

}

