package com.newbies.aircheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;


public class database extends SQLiteOpenHelper{

    public static final String db_name = "airCheck.db";
    public static final String table = "first_db";
    public static final String col1 = "ID";
    public static final String col2 = "time";
    public static final String col3 = "date";
    public static final String col4 = "age";
    public static final String col5 = "location";
    public static final String col6 = "itchy_eye";
    public static final String col7 = "cough";
    public static final String col8 = "sneeze";
    public static final String col9 = "nasal_obstruction";
    public static final String col10 = "airquality";
    public static final String col11 = "ashplumes";
    public static final String col12 = "smokeplumes";
    public static final String col13 = "relativehumidity";

    private static final int db_version = 1;
    database (Context context)
    {
        super(context, db_name, null, db_version);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + table + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, time TEXT,age INTEGER, location TEXT, itchy_eye INTEGER, " +
                "cough INTEGER, sneeze INTEGER, nasal_obstruction INTEGER, airquality REAL, ashplumes REAL, "+
                "smokeplumes REAL , relativehumidity REAL)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+table);
        onCreate(db);
    }
    public boolean insertData( String date, String time, int age,String location,
                               int itchy_eye, int cough, int sneeze,
                               int nasal_obstraction, double airquality, double ashplumes,
                               double smokeplumes,
                               double relativehumidity)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, date);
        contentValues.put(col3, time);
        contentValues.put(col4, age);
        contentValues.put(col5, location);
        contentValues.put(col6, itchy_eye);
        contentValues.put(col7, cough);
        contentValues.put(col8, sneeze);
        contentValues.put(col9, nasal_obstraction);
        contentValues.put(col10, airquality);
        contentValues.put(col11, ashplumes);
        contentValues.put(col12, smokeplumes);
        contentValues.put(col13, relativehumidity);
        long res = db.insert(table,null,contentValues);
        if(res == -1)
            return false;

        else return true;
    }
    public Cursor getData()
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+table,null);
        return cursor;
    }
}
