package com.example.admin.androidlocation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

/**
 * Created by Admin on 09/11/2017.
 */

public class loginadapter extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "log87.db";
    signup k = new signup();
    static int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    static final String tn0 = "LOGIN1";
    static final String DATABASE_CREATE3 = "create table " + tn0 + " ( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD text,role text,SHOPNAME text ) ";
    public SQLiteDatabase db;


    public loginadapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(loginadapter.DATABASE_CREATE3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to " + _newVersion + ", which will destroy all old data");
        _db.execSQL("DROP TABLE IF EXISTS " + tn0);
        onCreate(_db);
    }

    public loginadapter open() throws SQLException {
        db = this.getReadableDatabase();
        db.close();
        return this;
    }

    public void close() {
        //db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public long getProfilesCount() {
        SQLiteDatabase db = this.getDatabaseInstance();
        //db=this.getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, DATABASE_CREATE3);
        db.close();
        return cnt;
    }

    public void insertEntry(String userName, String password, String Role, String shopname) {
        db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", userName.trim());
        newValues.put("PASSWORD", password);
        newValues.put("role", Role.trim());
        newValues.put("SHOPNAME", shopname.trim());
        // Insert the row into your table
        db.insert(tn0, null, newValues);
        db.close();
    }

    public int deleteEntry(String UserName) {
        db = this.getWritableDatabase();
        String where = "USERNAME=?";
        int numberOFEntriesDeleted = db.delete(tn0, where, new String[]{UserName});
        db.close();
        return numberOFEntriesDeleted;

    }

    public String getpass(String userName) {
        db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.query(tn0, null, " USERNAME=?", new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            db.close();
            return null;
        }
        cursor.moveToFirst();
        if (cursor.getString(cursor.getColumnIndex("USERNAME")).equals(userName)) {
            db.close();
            return cursor.getString(cursor.getColumnIndex("PASSWORD"));
        }
        cursor.close();
        db.close();
        return null;
    }

    public String[] getuser(String r) {
        db = this.getWritableDatabase();
        Cursor cursor;
        String[] s = null;
        cursor = db.query(tn0, null, " role=?", new String[]{r}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            db.close();
            return null;
        }
        int i = 0;
        s = new String[cursor.getCount()];
        cursor.moveToFirst();
        if (cursor.getString(cursor.getColumnIndex("role")).equals(r)) {
            db.close();
            s[i++] = cursor.getString(cursor.getColumnIndex("USERNAME"));
        }
        cursor.close();
        db.close();
        return s;

    }

    public Cursor showall() {
        db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("SELECT * from " + tn0, null);
        return cursor;

    }

    public Boolean usercheck(String userName) {
        db = this.getReadableDatabase();
        Cursor cursor;
        //if(getProfilesCount()>0){
        cursor = db.rawQuery("SELECT * from " + tn0 + " where USERNAME='" + userName.trim() + "'", null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            db.close();
            return true;
        }
        cursor.close();
       /* }
        else
            db.execSQL(DATABASE_CREATE3);*/

        db.close();
        return false;
    }

    public void updateEntry(String userName, String password, String r) {
        db = this.getWritableDatabase();
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD", password);
        updatedValues.put("role", r);


        String where = "USERNAME = ?";
        db.update(tn0, updatedValues, where, new String[]{userName});
    }

    public void delall() {
        db = this.getWritableDatabase();
        this.onUpgrade(db, DATABASE_VERSION, DATABASE_VERSION++);
    }

    public String shopname(String username) {
        String firstname=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SHOPNAME FROM " + tn0 + " where USERNAME='" + username + "'", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    firstname = cursor.getString(cursor.getColumnIndex("SHOPNAME"));
                } while (cursor.moveToNext());
            }
        }
        return firstname;
    }
    public String shopname1() {
        String firstname=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SHOPNAME FROM " + tn0, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    firstname = cursor.getString(cursor.getColumnIndex("SHOPNAME"));
                    return  firstname;
                } while (cursor.moveToNext());
            }
        }
        return firstname;
    }
}