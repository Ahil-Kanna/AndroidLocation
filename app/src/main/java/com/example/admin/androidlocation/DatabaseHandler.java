package com.example.admin.androidlocation;

/**
 * Created by HP on 23-09-2017.
 */import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.view.View;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LocationOffer.db";
    public static final String TABLE_NAME = "locationOffers_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "LATITUDE";
    public static final String COL_3 = "LONGITUDE";
    public static final String COL_4 = "LOCATION";
    public static final String COL_5 = "SHOPNAME";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,LATITUDE TEXT,LONGITUDE TEXT,LOCATION TEXT,SHOPNAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String surname,String marks,String shopname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        contentValues.put(COL_5,shopname);
        Cursor c=db.rawQuery("SELECT * FROM "+TABLE_NAME+" where "+COL_5+"='"+shopname+"'",null);
        if(c.moveToFirst())
        {

        }
        else{
        long result = db.insert(TABLE_NAME,null ,contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;}
        return updateData(name,surname,marks,shopname);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "SHOPNAME = ?",new String[] {id});
    }
}
