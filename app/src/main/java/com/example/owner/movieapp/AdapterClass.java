package com.example.owner.movieapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Owner on 11-Oct-16.
 */

public class AdapterClass extends BaseAdapter {

    private Context context;
    public ArrayList<Movie> Data;

    public AdapterClass(Context c, ArrayList<Movie> data) {
        context = c;
        Data = data;
    }


    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) { //should return the actual object at the specified position in the adapter
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //position : The position of the item within the adapter's data set of the item whose view we want
    //convertView :  The old view to reuse, if possible.
    //parent : The parent that this view will eventually be attached to
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            //sets the width and height for the View—this ensures that, no matter the size of the drawable,
            //each image is resized and cropped to fit in these dimensions, as appropriate.
             imageView.setLayoutParams(new GridView.LayoutParams(240, 325));
            //declares that images should be cropped toward the center (if necessary).
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load(Data.get(position).getImage()).into(imageView);
        //imageView.setImageResource(Data.get(position).getImage());
        return imageView;

    }

}
