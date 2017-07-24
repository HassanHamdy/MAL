package com.example.owner.movieapp.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.owner.movieapp.Activities.FavouriteActivity;
import com.example.owner.movieapp.Adapter.AdapterClass;
import com.example.owner.movieapp.Data.Listener;
import com.example.owner.movieapp.Data.Movie;
import com.example.owner.movieapp.JsonParsing.Async;
import com.example.owner.movieapp.R;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private String myUrl;
    private RadioButton TopRated;
    private RadioButton MostPopular;
    private Dialog dialog;
    private Button OkDialogBtn;
    private Listener mListener;
    private GridView MoviesGridView;
    public static String API_KEY = "29666f5544f68b7f910faab81b8792ef";

    public void setmListener(Listener mListener) {
        this.mListener = mListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        myUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
        new Async(getActivity(), new Async.onResponse() {
            @Override
            public void onSuccess(final ArrayList data) {
                final ArrayList<Movie> movie = (ArrayList<Movie>) data;
                MoviesGridView = view.findViewById(R.id.Movies_Grid_View);
                MoviesGridView.setAdapter(new AdapterClass(view.getContext(), movie, 0, 0));
                MoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {
                        mListener.setSelectedItem(movie.get(position));
                    }
                });
            }
        }, 0).execute(myUrl);

        return view;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onChoose(View view) {
        if (TopRated.isChecked()) {
            myUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
            new Async(getActivity(), new Async.onResponse() {
                @Override
                public void onSuccess(final ArrayList data) {
                    final ArrayList<Movie> movie = (ArrayList<Movie>) data;
                    MoviesGridView = getView().findViewById(R.id.Movies_Grid_View);
                    MoviesGridView.setAdapter(new AdapterClass(getActivity(), movie, 0, 0));
                    MoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            mListener.setSelectedItem(movie.get(position));
                        }
                    });
                }
            }, 0).execute(myUrl);
            dialog.dismiss();
        } else if (MostPopular.isChecked()) {
            myUrl = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
            new Async(getActivity(), new Async.onResponse() {
                @Override
                public void onSuccess(final ArrayList data) {
                    final ArrayList<Movie> movie = (ArrayList<Movie>) data;
                    MoviesGridView = getView().findViewById(R.id.Movies_Grid_View);
                    MoviesGridView.setAdapter(new AdapterClass(getActivity(), movie, 0, 0));
                    MoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            mListener.setSelectedItem(movie.get(position));
                        }
                    });
                }
            }, 0).execute(myUrl);
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
            TopRated = dialog.findViewById(R.id.Top_reated);
            MostPopular = dialog.findViewById(R.id.Most_popular);
            OkDialogBtn = dialog.findViewById(R.id.dialog_button);
            OkDialogBtn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onChoose(v);
                }
            });

            dialog.show();
        } else if (id == R.id.favourite) {
            startActivity(new Intent(getActivity(), FavouriteActivity.class));
        }

        return true;
    }

}
