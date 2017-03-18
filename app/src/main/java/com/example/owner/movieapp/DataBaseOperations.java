package com.example.owner.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

import static com.example.owner.movieapp.TableData.TableInfo.DATABASE_NAME;
import static com.example.owner.movieapp.TableData.TableInfo.TABLE_NAME;


public class DataBaseOperations extends SQLiteOpenHelper {

    private ArrayList<Movie> Data;
    public static final int DATABASE_VERSION = 1;
    public String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(" + TableData.TableInfo.ID + " REAL," + TableData.TableInfo.Title + " TEXT," + TableData.TableInfo.Image
            + " TEXT," + TableData.TableInfo.RDate + " TEXT," + TableData.TableInfo.Rate + " REAL);";
    private SQLiteDatabase db;

    public DataBaseOperations(Context context) {
        super(context, TableData.TableInfo.TABLE_NAME, null, DATABASE_VERSION);
        db = context.openOrCreateDatabase(DATABASE_NAME, DATABASE_VERSION, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void StoreInDB(DataBaseOperations db, Movie data) {
        SQLiteDatabase sq = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.ID, data.getID());
        cv.put(TableData.TableInfo.Title, data.getTitle());
        cv.put(TableData.TableInfo.Image, data.getImage());
        cv.put(TableData.TableInfo.RDate, data.getRDate());
        cv.put(TableData.TableInfo.Rate, data.getRate());
        sq.insert(TableData.TableInfo.TABLE_NAME, null, cv);// will return long number that represent id number of row in DB

    }

    public Cursor RetrieveInfo(DataBaseOperations db) {
        SQLiteDatabase sq = db.getReadableDatabase();
        Cursor cr = sq.query(TableData.TableInfo.TABLE_NAME, null, null, null, null, null, null);
        return cr;
    }

    public boolean Search(DataBaseOperations db, int id) {
        SQLiteDatabase sq = db.getReadableDatabase();

        String query = "SELECT " + TableData.TableInfo.ID +
                " FROM " + TABLE_NAME +
                " WHERE " + TableData.TableInfo.ID + " == " + id + ";";
        Cursor cr = db.getReadableDatabase().rawQuery(query, null);

        if (!(cr.moveToFirst()) || cr.getCount() == 0) {
            return false;//empty
        } else
            return true;
    }

    public boolean DeleteRow(DataBaseOperations db, int id) {
        SQLiteDatabase sq = db.getWritableDatabase();
        if (Search(db, id)) {
            String query = "DELETE FROM " + TABLE_NAME + " WHERE " + TableData.TableInfo.ID + " == " + id + ";";
            sq.execSQL(query);
            return true;
        } else
            return false;

    }

}
