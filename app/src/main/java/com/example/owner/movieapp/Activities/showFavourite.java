package com.example.owner.movieapp.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.owner.movieapp.Fragments.showfavFragment;
import com.example.owner.movieapp.R;


public class showFavourite extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav);

        showfavFragment sFr = new showfavFragment();
        FragmentManager sFm = getFragmentManager();
        FragmentTransaction fragmentTransaction = sFm.beginTransaction();
        fragmentTransaction.add(R.id.fContainer, sFr, "Favourite");
        fragmentTransaction.commit();

    }
}
