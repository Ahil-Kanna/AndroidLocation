package com.example.admin.androidlocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Admin on 10/11/2017.
 */

public class offerdata extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "offers1.db";
    public static final String TABLE_NAME = "Offers_table";
    public static final String COL_1 = "ID";
    public static final String COL_5 = "OFFERS";
    public static final String COL_4 = "USERNAME";
    public static final String COL_6 = "SHOPNAME";
    public SQLiteDatabase db;
    loginadapter loginadapter;
    ArrayAdapter<String> m_adapter;
    ListView results;

    public offerdata open() {
        db = this.getReadableDatabase();
        db.close();
        return this;
    }

    public offerdata(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,OFFERS TEXT,SHOPNAME TEXT,USERNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*public offerdata open() throws SQLException {
        db = this.getReadableDatabase();
        db.close();
        return this;
    }
*/
    public boolean insertData(String Offers, String shopname, String uname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, uname.trim());
        contentValues.put(COL_5, Offers.trim());
        contentValues.put(COL_6, shopname.trim());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;

    }

    public boolean updateData(String sn, String of1, String of2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.execSQL("UPDATE "+TABLE_NAME+" set "+COL_5+"='"+of2.trim()+"' where "+COL_5+"='"+of1+"'");
        return true;
    }

    public void deleteData(String offer)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_5 + " = ?", new String[] { offer });

    }
    public int checkUser(String us) {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT COL_5 FROM user WHERE COL6=?",new String[]{us});
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            id=cursor.getInt(0);
            cursor.close();
        }
        return id;

    }
    public ArrayList<String> openAndQueryDatabase(String username) {
        ArrayList<String> offList = new ArrayList<String>();
            SQLiteDatabase db=this.getReadableDatabase();
            Cursor cursor=db.rawQuery("SELECT "+ COL_5+ " FROM " + TABLE_NAME+" where USERNAME = '"+ username+"'",null);
            if (cursor != null )
            {if (cursor.moveToFirst()) {
                    do {
                        String firstName = cursor.getString(cursor.getColumnIndex(COL_5));
                        offList.add(firstName);
                    }while (cursor.moveToNext());
                }
            }

        return offList;
    }

    private void displayResultList() {

     /*   setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);
        */
    }

    }