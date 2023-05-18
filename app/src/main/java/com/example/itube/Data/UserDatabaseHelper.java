package com.example.itube.Data;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.itube.Util.Util;


public class UserDatabaseHelper extends SQLiteOpenHelper {

    public UserDatabaseHelper(@Nullable Context context) {
        super(context, Util.USER_DATABASE, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.USER_TABLE + "("
                + Util.USER + " TEXT PRIMARY KEY, "
                + Util.PASSWORD + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + Util.USER_TABLE;
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        onCreate(sqLiteDatabase);
    }

    //inserts a new username and password
    public long insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.USER, username);
        contentValues.put(Util.PASSWORD, password);
        long result = db.insert(Util.USER_TABLE, null, contentValues);
        db.close();
        return result;
    }

    //check if the username is in the database
    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USER_TABLE, new String[]{Util.USER},
                Util.USER + "=?", new String[]{username}, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    //check if log on details are valid
    public boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.USER_TABLE, new String[]{Util.USER},
                Util.USER + "=? AND " + Util.PASSWORD + "=?",
                new String[]{username, password}, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
}


