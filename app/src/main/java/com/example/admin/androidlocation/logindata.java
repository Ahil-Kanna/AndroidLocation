package com.example.admin.androidlocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 09/11/2017.
 */

public class logindata extends SQLiteOpenHelper {

    static final String APP_DB = "logs.db";
    static int DB_VER = 1;
    final String DATABASE_CREATE1 = "create table " + "open" + "( " + "ID" + " integer primary key autoincrement,Role text,user text) ";
    public SQLiteDatabase db;

    public logindata(Context context) {
        super(context, APP_DB, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(DATABASE_CREATE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to " + _newVersion + ", which will destroy all old data");
        _db.execSQL("DROP TABLE IF EXISTS " + "open");
        onCreate(_db);
    }

    public void newdet(String a, String b) {
        db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("role", a);
        newValues.put("user", b);
        db.insert("open", null, newValues);
        db.close();
    }

    public void cleardet() {
        db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS open");
        //dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db.execSQL(DATABASE_CREATE1);
        db.close();

    }

    public String getdet() {
        String a = null;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + "open", null);
        if (cursor != null) {
            if (cursor.moveToLast()) {

                a = cursor.getString(cursor.getColumnIndex("role"));
                db.close();
                return a;
            }

        }
        db.close();
        return null;
    }

    public String getuser() {
        String a = null;

        db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from " + "open", null);
        if (cursor != null) {
            if (cursor.moveToLast()) {

                a = cursor.getString(cursor.getColumnIndex("user"));
                db.close();
                return a;
            }

        }
        db.close();
        return null;

    }
}
