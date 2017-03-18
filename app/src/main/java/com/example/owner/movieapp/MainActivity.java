package com.example.owner.movieapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Listener {
    boolean mIsTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);


        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            mIsTwoPane = false;

            //check if 2 pane
            if (null != findViewById(R.id.dContainer)) {
                mIsTwoPane = true;

            }

            MainActivityFragment mFr = new MainActivityFragment();
            FragmentManager mFm = getFragmentManager();
            FragmentTransaction fragmentTransaction = mFm.beginTransaction();
            fragmentTransaction.replace(R.id.aContainer, mFr, "Main");
            mFr.setmListener(this);
            fragmentTransaction.commit();
        } else {
            Toast.makeText(this, "No Internet Connection\ncheck your connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSelectedItem(Movie Data) {
        if (mIsTwoPane == false || getResources().getConfiguration().orientation != 2) {
            startActivity(new Intent(this, Detail_view.class).putExtra("data", Data));
        } else {
            Detail_Fragment dFr = new Detail_Fragment();
            Bundle extras = new Bundle();
            extras.putSerializable("data", Data);
            dFr.setArguments(extras);
            FragmentManager dFm = getFragmentManager();
            FragmentTransaction fragmentTransaction = dFm.beginTransaction();
            fragmentTransaction.replace(R.id.dContainer, dFr, "Detail");
            fragmentTransaction.commit();
        }
    }
}