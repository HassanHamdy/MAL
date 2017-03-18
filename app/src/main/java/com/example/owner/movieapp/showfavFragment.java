package com.example.owner.movieapp;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;


public class showfavFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fav_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Movie> FavData = new ArrayList<Movie>();

        DataBaseOperations db = new DataBaseOperations(getActivity());

        Cursor cr = db.RetrieveInfo(db);

        if (cr.moveToFirst()) {

            do {
                FavData.add(new Movie(cr.getInt(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getDouble(4)));
            } while (cr.moveToNext());
        } else
            Toast.makeText(getActivity(), "No Movie Added ", Toast.LENGTH_SHORT).show();

        cr.close();
        db.close();

        GridView gridView = (GridView) getView().findViewById(R.id.grid);
        gridView.setAdapter(new AdapterClass(getActivity(), FavData));
    }
}
