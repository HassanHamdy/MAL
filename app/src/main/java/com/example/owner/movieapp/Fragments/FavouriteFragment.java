package com.example.owner.movieapp.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.owner.movieapp.Adapter.AdapterClass;
import com.example.owner.movieapp.DB.DataBaseOperations;
import com.example.owner.movieapp.Data.Movie;
import com.example.owner.movieapp.R;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {

    private String message = "No movie added \nGo to movie and add your Favourite Movies";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Movie> FavouriteData = new ArrayList<>();

        DataBaseOperations db = new DataBaseOperations(getActivity());

        Cursor cr = db.RetrieveInfo(db);

        if (cr.moveToFirst()) {

            do {
                FavouriteData.add(new Movie(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getDouble(4)));
            } while (cr.moveToNext());
        } else
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

        cr.close();
        db.close();

        GridView gridView = getView().findViewById(R.id.FavouriteGridView);
        gridView.setAdapter(new AdapterClass(getActivity(), FavouriteData, 0, 0));
    }
}
