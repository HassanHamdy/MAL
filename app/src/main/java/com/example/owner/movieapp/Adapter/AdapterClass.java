package com.example.owner.movieapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owner.movieapp.Activities.Detail_view;
import com.example.owner.movieapp.Activities.MainActivity;
import com.example.owner.movieapp.Activities.showFavourite;
import com.example.owner.movieapp.Data.Movie;
import com.example.owner.movieapp.Data.Reviews;
import com.example.owner.movieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class AdapterClass extends ArrayAdapter {

    private Context context;
    public ArrayList Data;
    private int check, frag;
    private ArrayList<String> StringData;
    private ArrayList<Reviews> ReviewData;
    private ArrayList<Movie> MovieData;
    private Detail_view df = new Detail_view();
    private MainActivity mf = new MainActivity();
    private showFavourite sf = new showFavourite();


    public AdapterClass(Context c, ArrayList data, int Check, int Frag) {
        super(c, -1);
        context = c;
        Data = data;
        frag = Frag;
        if (Check == 0) {
            MovieData = (ArrayList<Movie>) Data;
        } else if (Check == 1) {
            ReviewData = (ArrayList<Reviews>) Data;
        } else {
            StringData = (ArrayList<String>) Data;
        }
        check = Check;
    }

    @Override
    public int getCount() {
        int size;

        if (check == 0) {
            size = MovieData.size();
        } else if (check == 1) {
            size = ReviewData.size();
        } else {
            size = StringData.size();
        }
        return size;
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
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("HASSAN", context.getClass().getSimpleName());
        Log.d("HASSAN", "getView: " + position);

        View rowView = convertView;
        if (context.getClass().getSimpleName().equals(mf.getClass().getSimpleName()) ||
                context.getClass().getSimpleName().equals(sf.getClass().getSimpleName())) {


            if (frag == 0) {

                viewHolderItem viewHolder;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    rowView = inflater.inflate(R.layout.gridviewitem, parent, false);
                    viewHolder = new viewHolderItem();
                    viewHolder.imageView = rowView.findViewById(R.id.imageView);
                    // store the holder with the view.
                    rowView.setTag(viewHolder);

                } else {
                    viewHolder = (viewHolderItem) convertView.getTag();
                }

                Picasso.with(context).load(MovieData.get(position).getImage()).into(viewHolder.imageView);
                rowView.setTag(viewHolder);

            } else if (frag == 1 && check == 1) {
                AdapterClass.viewHolderItem viewHolder;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    rowView = inflater.inflate(R.layout.review_items, parent, false);
                    viewHolder = new AdapterClass.viewHolderItem();
                    viewHolder.imageView1 = rowView.findViewById(R.id.ownerImage);
                    viewHolder.OW_NameTextView = rowView.findViewById(R.id.name);
                    viewHolder.contentTextView = rowView.findViewById(R.id.review);
                    // store the holder with the view.
                    rowView.setTag(viewHolder);

                } else {
                    viewHolder = (AdapterClass.viewHolderItem) convertView.getTag();
                }

                viewHolder.imageView1.setImageResource(ReviewData.get(position).getImgID());
                viewHolder.OW_NameTextView.setText(ReviewData.get(position).getAuthor_Name());
                viewHolder.contentTextView.setText(ReviewData.get(position).getContent());
            } else if (frag == 1 && check == 2) {

                AdapterClass.viewHolderItem viewHolder;

                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    rowView = inflater.inflate(R.layout.youtube_items, parent, false);
                    viewHolder = new AdapterClass.viewHolderItem();
                    viewHolder.youtube_img = rowView.findViewById(R.id.youtube_img);
                    viewHolder.txtName = rowView.findViewById(R.id.NameVideo);
                    // store the holder with the view.
                    rowView.setTag(viewHolder);

                } else {
                    viewHolder = (AdapterClass.viewHolderItem) convertView.getTag();
                }

                viewHolder.youtube_img.setImageResource(R.drawable.youtubeimg);
                viewHolder.youtube_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StringData.get(position)));
                        context.startActivity(webIntent);
                    }
                });

                viewHolder.txtName.setText("Trailer ( " + (position + 1) + " )");
                rowView.setTag(viewHolder);

            }


        } else if (context.getClass().getSimpleName().equals(df.getClass().getSimpleName()) &&
                check == 1) {

            AdapterClass.viewHolderItem viewHolder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.review_items, parent, false);
                viewHolder = new AdapterClass.viewHolderItem();
                viewHolder.imageView1 = rowView.findViewById(R.id.ownerImage);
                viewHolder.OW_NameTextView = rowView.findViewById(R.id.name);
                viewHolder.contentTextView = rowView.findViewById(R.id.review);
                // store the holder with the view.
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (AdapterClass.viewHolderItem) convertView.getTag();
            }

            viewHolder.imageView1.setImageResource(ReviewData.get(position).getImgID());
            viewHolder.OW_NameTextView.setText(ReviewData.get(position).getAuthor_Name());
            viewHolder.contentTextView.setText(ReviewData.get(position).getContent());


        } else if (context.getClass().getSimpleName().equals(df.getClass().getSimpleName()) &&
                check == 2) {


            AdapterClass.viewHolderItem viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.youtube_items, parent, false);
                viewHolder = new AdapterClass.viewHolderItem();
                viewHolder.youtube_img = rowView.findViewById(R.id.youtube_img);
                viewHolder.txtName = rowView.findViewById(R.id.NameVideo);
                // store the holder with the view.
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (AdapterClass.viewHolderItem) convertView.getTag();
            }

            viewHolder.youtube_img.setImageResource(R.drawable.youtubeimg);
            viewHolder.youtube_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(StringData.get(position)));
                    context.startActivity(webIntent);
                }
            });

            viewHolder.txtName.setText("Trailer ( " + (position + 1) + " )");
            rowView.setTag(viewHolder);

        }
        return rowView;

    }

    private class viewHolderItem {
        ImageView imageView;

        ImageView imageView1;
        TextView OW_NameTextView;
        TextView contentTextView;

        ImageView youtube_img;
        TextView txtName;
    }


}
