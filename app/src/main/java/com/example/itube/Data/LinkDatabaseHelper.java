package com.example.itube.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.itube.Model.linkPair;
import com.example.itube.Util.Util;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LinkDatabaseHelper extends SQLiteOpenHelper {

    public LinkDatabaseHelper(@Nullable Context context)
    {
        super(context, Util.PLAYLIST_DATABASE, null, Util.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " + Util.PLAYLIST_TABLE + "("
                + Util.VIDEO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Util.USER + " TEXT, "
                + Util.URL + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_PLAYLIST_TABLE = "DROP TABLE IF EXISTS " + Util.PLAYLIST_TABLE;
        sqLiteDatabase.execSQL(DROP_PLAYLIST_TABLE);

        onCreate(sqLiteDatabase);
    }

    //takes linkPair as argument, uses the getters from its class to update the table
    public void insertPair(linkPair pair)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.USER, pair.getUser());
        values.put(Util.URL, pair.getLink());
        db.insert(Util.PLAYLIST_TABLE, null, values);
        db.close();
    }


    //takes string as argument, returns arrayList of linkPair objects which makes the users playlist
    public ArrayList<linkPair> getPairs(String username) {
        ArrayList<linkPair> pairs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.PLAYLIST_TABLE +
                " WHERE " + Util.USER + " = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                //user column index is 1
                String user = cursor.getString(1);
                //link column index is 2
                String link = cursor.getString(2);
                linkPair pair = new linkPair(user, link);
                pairs.add(pair);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pairs;
    }

    //takes a link pair and checks if it is in the database already. Returns isNew boolean based on comparison of the cursor count (if count == 0, isNew = true)
    public boolean checkIsNew(linkPair pair) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = Util.USER + " = ? AND " + Util.URL + " = ?";
        String[] selectionArgs = { pair.getUser(), pair.getLink() };
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.PLAYLIST_TABLE +
                " WHERE " + selection, selectionArgs);
        boolean isNew = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isNew;
    }




}
