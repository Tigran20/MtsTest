package com.example.system.testmts.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private static CowDbHelper dbHelper;
    private static Data data;
    private static List<Cow> list;

    public Data() {
    }

    public static List<Cow> getProductData(Context context) {
        if (data == null) {
            data = new Data();
        }
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list.clear();
        }

        dbHelper = new CowDbHelper(context);

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String[] projection = {
                CowContract.CowEntry._ID,
                CowContract.CowEntry.COLUMN_COW_BREED,
                CowContract.CowEntry.COLUMN_COW_SUIT,
                CowContract.CowEntry.COLUMN_COW_AGE
        };

        Cursor cursor = database.query(
                CowContract.CowEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(CowContract.CowEntry._ID);
            int breedColumnIndex = cursor.getColumnIndex(CowContract.CowEntry.COLUMN_COW_BREED);
            int suitColumnIndex = cursor.getColumnIndex(CowContract.CowEntry.COLUMN_COW_SUIT);
            int ageColumnIndex = cursor.getColumnIndex(CowContract.CowEntry.COLUMN_COW_AGE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                String currentSuit = cursor.getString(suitColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);
                list.add(new Cow(currentId, currentBreed, currentSuit, currentAge));
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public static void insertData(String cowBreed, String cowSuit, int cowAge) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CowContract.CowEntry.COLUMN_COW_BREED, cowBreed);
        contentValues.put(CowContract.CowEntry.COLUMN_COW_SUIT, cowSuit);
        contentValues.put(CowContract.CowEntry.COLUMN_COW_AGE, cowAge);
        database.insert(CowContract.CowEntry.TABLE_NAME, null, contentValues);
    }

    public static void deleteData(long id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(CowContract.CowEntry.TABLE_NAME, CowContract.CowEntry._ID + " = " + id, null);
    }

    public static void updateData(long id, String cowBreed, String cowSuit, int cowAge) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CowContract.CowEntry.COLUMN_COW_BREED, cowBreed);
        contentValues.put(CowContract.CowEntry.COLUMN_COW_SUIT, cowSuit);
        contentValues.put(CowContract.CowEntry.COLUMN_COW_AGE, cowAge);
        database.update(CowContract.CowEntry.TABLE_NAME, contentValues, CowContract.CowEntry._ID + " = " + id, null);
    }
}
