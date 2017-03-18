package com.example.owner.movieapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class youtube_adapter extends ArrayAdapter {

    private Context context;
    public ArrayList<String> Data;
    private String Name;

    public youtube_adapter(Context c, ArrayList<String> data, String name) {
        super(c, -1, data);
        context = c;
        Data = data;
        Name = name;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolderItem viewHolder;
        View rowView = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.youtube_items, parent, false);
            viewHolder = new viewHolderItem();
            viewHolder.youtube_img = (ImageView) rowView.findViewById(R.id.youtube_img);
            viewHolder.txtName = (TextView) rowView.findViewById(R.id.NameVideo);
            // store the holder with the view.
            rowView.setTag(viewHolder);

        } else {
            viewHolder = (viewHolderItem) convertView.getTag();
        }

        viewHolder.youtube_img.setImageResource(R.drawable.youtubeimg);
        viewHolder.youtube_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Data.get(position)));
                context.startActivity(webIntent);
            }
        });

        viewHolder.txtName.setText(Name + " Trailer (" + (position + 1) + ")");
        rowView.setTag(viewHolder);

        return rowView;

    }

    private class viewHolderItem {
        ImageView youtube_img;
        TextView txtName;
    }
}
