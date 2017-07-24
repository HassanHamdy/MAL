package com.example.owner.movieapp.Fragments;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.movieapp.Adapter.AdapterClass;
import com.example.owner.movieapp.DB.DataBaseOperations;
import com.example.owner.movieapp.Data.Movie;
import com.example.owner.movieapp.Data.Reviews;
import com.example.owner.movieapp.JsonParsing.Async;
import com.example.owner.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DetailFragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private FloatingActionButton fb_add;
    private FloatingActionButton fb_remove;
    private Movie data;
    private ArrayList<Integer> IDsOfFavouriteMovies = new ArrayList<>();
    private String AddMovieToFavourite = "Movie is added to favourite";
    private String MovieAlreadyInFavourite = "This Movie is added later";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        DataBaseOperations db = new DataBaseOperations(getActivity());

        Cursor cr = db.RetrieveInfo(db);

        if (cr.moveToFirst()) {
            do {
                int id = cr.getInt(0);
                IDsOfFavouriteMovies.add(id);
            } while (cr.moveToNext());
        }

        cr.close();
        db.close();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("HASSAN", String.valueOf(getActivity()));

        data = (Movie) getArguments().getSerializable("data");
        ImageView backImg = getView().findViewById(R.id.background_img);
        Picasso.with(getActivity()).load(data.getBackgroundImage()).into(backImg);

        fb_add = getView().findViewById(R.id.fabAdd);
        fb_add.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#FFFFFF")));

        if (IDsOfFavouriteMovies.contains(data.getID()))
            fb_add.setImageResource(R.drawable.heart);
        else
            fb_add.setImageResource(R.drawable.tick);

        fb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClick(v);
            }
        });

        fb_remove = getView().findViewById(R.id.fabRemove);
        fb_remove.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#FFFFFF")));

        fb_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveClick(v);
            }
        });

        collapsingToolbarLayout = getView().findViewById(R.id.collapseBar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setTitle(data.getTitle());


        ImageView imgMoviePoster = getView().findViewById(R.id.moviePoster);
        Picasso.with(getActivity()).load(data.getImage()).into(imgMoviePoster);

        RatingBar MovieRating = getView().findViewById(R.id.movieRate);
        MovieRating.setRating((float) data.getRate());

        TextView txtReleaseDate = getView().findViewById(R.id.txtReleaseDate);
        txtReleaseDate.setText(data.getRDate());


        TextView txtDescription = getView().findViewById(R.id.txtDescription);
        txtDescription.setText(data.getDescription());


        String myUrl = "http://api.themoviedb.org/3/movie/" + data.getID() + "/reviews?api_key=" + MainFragment.API_KEY;
        new Async(getActivity(), new Async.onResponse() {
            @Override
            public void onSuccess(ArrayList data) {
                ListView ReviewsListView = getView().findViewById(R.id.reviewListView);
                ReviewsListView.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                ReviewsListView.setAdapter(new AdapterClass(getActivity(), (ArrayList<Reviews>) data, 1, 1));
            }
        }, 1).execute(myUrl);


        String URL = "http://api.themoviedb.org/3/movie/" + data.getID() + "/videos?api_key=" + MainFragment.API_KEY;
        new Async(getActivity(), new Async.onResponse() {
            @Override
            public void onSuccess(ArrayList data) {
                ListView YoutubeListView = getView().findViewById(R.id.youtubeListView);
                YoutubeListView.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                YoutubeListView.setAdapter(new AdapterClass(getActivity(), (ArrayList<String>) data, 2, 1));
            }
        }, 2).execute(URL);


    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, RelativeLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public void AddClick(View view) {
        DataBaseOperations DB = new DataBaseOperations(getActivity());
        boolean x = DB.Search(DB, data.getID());
        if (!x) {
            DB.StoreInDB(DB, data);
            Toast.makeText(getActivity(), AddMovieToFavourite, Toast.LENGTH_SHORT).show();
            fb_add.setImageResource(R.drawable.heart);
        } else {
            Toast.makeText(getActivity(), MovieAlreadyInFavourite, Toast.LENGTH_SHORT).show();
            fb_add.setImageResource(R.drawable.heart);
        }
    }

    public void RemoveClick(View view) {
        DataBaseOperations DB = new DataBaseOperations(getActivity());
        if (DB.DeleteRow(DB, data.getID())) {
            Toast.makeText(getActivity(), "Movie is deleted from favourite", Toast.LENGTH_SHORT).show();
            fb_add.setImageResource(R.drawable.tick);
        } else {
            Toast.makeText(getActivity(), "Movie is not in favourite", Toast.LENGTH_SHORT).show();
            fb_add.setImageResource(R.drawable.tick);
        }

    }


}
