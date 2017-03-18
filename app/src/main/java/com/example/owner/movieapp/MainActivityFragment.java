package com.example.owner.movieapp;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivityFragment extends Fragment {

    String myUrl;
    RadioButton rd1;
    RadioButton rd2;
    Dialog dialog;
    Button btn;
    private Listener mListener;
    private String API_KEY ="29666f5544f68b7f910faab81b8792ef";

    public void setmListener(Listener mListener) {
        this.mListener = mListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.main_activity_fragment, container, false);
        myUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
        new Async(getActivity(), new Async.onResponse() {
            @Override
            public void onSuccess(final ArrayList<Movie> data) {
                GridView gridView = (GridView) view.findViewById(R.id.Grid_View);
                gridView.setAdapter(new AdapterClass(view.getContext(), data));
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        mListener.setSelectedItem(data.get(position));
                    }
                });
            }
        }).execute(myUrl);

        return view;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onChoose(View view) {
        if (rd1.isChecked()) {
            myUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
            new Async(getActivity(), new Async.onResponse() {
                @Override
                public void onSuccess(final ArrayList<Movie> data) {
                    GridView gridView = (GridView) getView().findViewById(R.id.Grid_View);
                    gridView.setAdapter(new AdapterClass(getActivity(), data));
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            mListener.setSelectedItem(data.get(position));
                        }
                    });
                }
            }).execute(myUrl);
            dialog.dismiss();
        } else if (rd2.isChecked()) {
            myUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
            new Async(getActivity(), new Async.onResponse() {
                @Override
                public void onSuccess(final ArrayList<Movie> data) {
                    GridView gridView = (GridView) getView().findViewById(R.id.Grid_View);
                    gridView.setAdapter(new AdapterClass(getActivity(), data));
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            mListener.setSelectedItem(data.get(position));
                        }
                    });
                }
            }).execute(myUrl);
            dialog.dismiss();
        } else
            Toast.makeText(getActivity(), "Please Choose one ..!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title
            dialog.setContentView(R.layout.dialogue_box);
            rd1 = (RadioButton) dialog.findViewById(R.id.Top_reated);
            rd2 = (RadioButton) dialog.findViewById(R.id.Most_popular);
            btn = (Button) dialog.findViewById(R.id.dialog_button);
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onChoose(v);
                }
            });

            dialog.show();
        } else if (id == R.id.favourite) {
            startActivity(new Intent(getActivity(), showFavourite.class));
        }

        return true;
    }

}
