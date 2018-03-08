package com.example.sam.hikingoregon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sam on 3/8/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "hikes.db";
    public static final String TABLE_NAME = "student_table";

    //creating columns for DB
    public static final String COL_1 = "ID";
    public static final String COL_2 = "HIKE";
    public static final String COL_3 = "DESCRIPTION";
    public static final String COL_4 = "LENGTH";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, HIKE TEXT, DESCRIPTION TEXT, LENGTH TEXT )"); //setting column types
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // if table is created drop table
        onCreate(db);
    }

    /*
    Method inserts data into the Database given the text values in the text boxes.
     */
    public boolean insertData(String hike, String description, String length){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, hike);
        contentValues.put(COL_3, description);
        contentValues.put(COL_4, length);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        }else{
            return true;
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res =  db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

}
