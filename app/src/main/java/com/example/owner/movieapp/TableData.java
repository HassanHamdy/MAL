package com.example.owner.movieapp;

import android.provider.BaseColumns;


public class TableData {

    public static abstract class TableInfo implements BaseColumns {
        public static final String ID = "Movie_ID";
        public static final String Image = "Path";
        public static final String Title = "title";
        public static final String Rate = "rate";
        public static final String RDate = "release_date";
        public static final String DATABASE_NAME = "user_fav";
        public static final String TABLE_NAME = "Favourite";
    }
}
