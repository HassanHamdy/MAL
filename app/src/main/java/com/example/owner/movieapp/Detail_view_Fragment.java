package com.example.owner.movieapp;

import android.app.Fragment;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Detail_view_Fragment extends Fragment {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    FloatingActionButton fb_add;
    FloatingActionButton fb_remove;
    Movie data;
    ArrayList<Integer> mIDs = new ArrayList<Integer>();
    String NAME = "";
    private String API_KEY ="29666f5544f68b7f910faab81b8792ef";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.details_fragment, container, false);

        DataBaseOperations db = new DataBaseOperations(getActivity());

        Cursor cr = db.RetrieveInfo(db);

        if (cr.moveToFirst()) {
            do {
                int id = cr.getInt(0);
                mIDs.add(id);
            } while (cr.moveToNext());
        }

        cr.close();
        db.close();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        data = (Movie) getActivity().getIntent().getSerializableExtra("data");


        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ImageView backImg = (ImageView) getView().findViewById(R.id.background_img);
        Picasso.with(getActivity()).load(data.getBackgroundImage()).into(backImg);

        fb_add = (FloatingActionButton) getView().findViewById(R.id.fabAdd);
        fb_add.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#FFFFFF")));
        if (mIDs.contains(data.getID()))
            fb_add.setImageResource(R.drawable.heart);
        else
            fb_add.setImageResource(R.drawable.tick);

        fb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddClick(v);
            }
        });

        fb_remove = (FloatingActionButton) getView().findViewById(R.id.fabRemove);
        fb_remove.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#FFFFFF")));

        fb_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveClick(v);
            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) getView().findViewById(R.id.collapseBar);
        collapsingToolbarLayout.setTitle(data.getTitle());


        NAME = data.getTitle();


        ImageView image = (ImageView) getView().findViewById(R.id.imageView);
        Picasso.with(getActivity()).load(data.getImage()).into(image);

        RatingBar Rate = (RatingBar) getView().findViewById(R.id.rating_bar);
        Rate.setRating((float) data.getRate());

        TextView rdate = (TextView) getView().findViewById(R.id.textView_ReleaseDate);
        rdate.setText(data.getRDate());


        TextView description = (TextView) getView().findViewById(R.id.textView_description);
        description.setText(data.getDescription());


        String myUrl = "http://api.themoviedb.org/3/movie/" + data.getID() + "/reviews?api_key=" + API_KEY;
        new ReviewsAsyncTask(getActivity(), new ReviewsAsyncTask.onResponse() {
            @Override
            public void onSuccess(ArrayList<Reviews> data) {
                ListView listView = (ListView) getView().findViewById(R.id.listreview);
                listView.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                setListViewHeightBasedOnChildren(listView);
                listView.setAdapter(new ReviewAdapter(getActivity(), data));
            }
        }).execute(myUrl);


        String URL = "http://api.themoviedb.org/3/movie/" + data.getID() + "/videos?api_key=" + API_KEY;
        new youtubeAsync(getActivity(), new youtubeAsync.onResponse() {
            @Override
            public void onSuccess(ArrayList<String> data) {
                ListView listView = (ListView) getView().findViewById(R.id.listYT);
                listView.setOnTouchListener(new View.OnTouchListener() {
                    // Setting on Touch Listener for handling the touch inside ScrollView
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Disallow the touch request for parent scroll on touch of child view
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;
                    }
                });
                setListViewHeightBasedOnChildren(listView);
                listView.setAdapter(new youtube_adapter(getActivity(), data, NAME));
            }
        }).execute(URL);


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
            Toast.makeText(getActivity(), "Movie is added to favourite", Toast.LENGTH_SHORT).show();
            fb_add.setImageResource(R.drawable.heart);
        } else {
            Toast.makeText(getActivity(), "you added this movie later", Toast.LENGTH_SHORT).show();
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
