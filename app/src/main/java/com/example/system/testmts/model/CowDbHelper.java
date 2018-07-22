package com.example.system.testmts.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CowDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cows.db";
    public static final int DATABASE_VERSION = 1;


    public CowDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + CowContract.CowEntry.TABLE_NAME + " ("
                + CowContract.CowEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CowContract.CowEntry.COLUMN_COW_BREED + " TEXT NOT NULL, "
                + CowContract.CowEntry.COLUMN_COW_SUIT + " TEXT NOT NULL, "
                + CowContract.CowEntry.COLUMN_COW_AGE + " NOT NULL DEFAULT 0 "
                + ")";
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
