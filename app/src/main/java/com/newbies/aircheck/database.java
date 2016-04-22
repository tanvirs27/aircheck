package com.newbies.aircheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.widget.RadioGroup;


public class database extends SQLiteOpenHelper{

    public static final String db_name = "airCheck.db";
    public static final String table = "first_db";
    public static final String col1 = "ID";
    public static final String col2 = "time";
    public static final String col3 = "date";
    public static final String col4 = "age";
    public static final String col5 = "location";
    public static final String col6 = "country";
    public static final String col7 = "itchy_eye";
    public static final String col8 = "cough";
    public static final String col9 = "sneeze";
    public static final String col10 = "nasal_obstruction";
    public static final String col11 = "asthma";
    public static final String col12 = "chest_pain";

    public static final String col13 = "airquality";
    public static final String col14 = "ashplumes";
    public static final String col15 = "smokeplumes";
    public static final String col16 = "relativehumidity";

    private static final int db_version = 3;
    database (Context context)
    {
        super(context, db_name, null, db_version);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // db.execSQL("DELETE FROM "+table);
        db.execSQL("create table " + table + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date TEXT, time TEXT,age INTEGER, location TEXT,country TEXT, itchy_eye INTEGER, " +
                "cough INTEGER, sneeze INTEGER, nasal_obstruction INTEGER, asthma INTEGER, chest_pain INTEGER, " +
                " airquality REAL, ashplumes REAL, "+
                "smokeplumes REAL , relativehumidity REAL)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+table);
        onCreate(db);
    }
    public boolean insertData( String date, String time, int age,String location,String country,
                               int itchy_eye, int cough, int sneeze,
                               int nasal_obstraction, int asthma, int chest_pain, double airquality, double ashplumes,
                               double smokeplumes,
                               double relativehumidity)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, date);
        contentValues.put(col3, time);
        contentValues.put(col4, age);
        contentValues.put(col5, location);
        contentValues.put(col6, country);
        contentValues.put(col7, itchy_eye);
        contentValues.put(col8, cough);
        contentValues.put(col9, sneeze);
        contentValues.put(col10, nasal_obstraction);
        contentValues.put(col11, asthma);
        contentValues.put(col12, chest_pain);
        contentValues.put(col13, airquality);
        contentValues.put(col14, ashplumes);
        contentValues.put(col15, smokeplumes);
        contentValues.put(col16, relativehumidity);
        long res = db.insert(table,null,contentValues);
        if(res == -1)
            return false;

        else return true;
    }

    public Cursor getData(String location,int[] val)
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from first_db where "+ col1 +"= 1", null);
        return cursor;
    }
}