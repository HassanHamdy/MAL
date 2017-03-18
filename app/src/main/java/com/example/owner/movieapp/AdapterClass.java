package com.example.owner.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class AdapterClass extends ArrayAdapter {

    private Context context;
    public ArrayList<Movie> Data;

    public AdapterClass(Context c, ArrayList<Movie> data) {
        super(c, -1, data);
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
        viewHolderItem viewHolder;
        View rowView = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.gridviewitem, parent, false);
            viewHolder = new viewHolderItem();
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
            // store the holder with the view.
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (viewHolderItem) convertView.getTag();
        }

        Picasso.with(context).load(Data.get(position).getImage()).into(viewHolder.imageView);
        rowView.setTag(viewHolder);
        return rowView;

    }

    private class viewHolderItem {
        ImageView imageView;
    }


}
