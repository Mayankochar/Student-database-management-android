package com.example.settingcheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATBASE_NAME = "Student.db";
    public final static String TABLE_NAME = "PAGALHAITU";
    public final static String COL_1 = "ID";
    public final static String COL_2 = "NAME";
    public final static String COL_3 = "EMAIL";
    public final static String COL_4 = "COURSE_COUNT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATBASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY,NAME TEXT,EMAIL TEXT,COURSE_COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean InsertData(String id,String name, String email, String cc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,id);
        cv.put(COL_2, name);
        cv.put(COL_3, email);
        cv.put(COL_4, cc);
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            return false;
        } else
            return true;
    }

    public boolean UpdateData(String id, String name, String email, String cc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, id);
        cv.put(COL_2, name);
        cv.put(COL_3, email);
        cv.put(COL_4, cc);
        db.update(TABLE_NAME, cv, "ID=?", new String[]{id});
        return true;
    }

    public Cursor ViewData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("SELECT * FROM " + TABLE_NAME + " WHERE ID='" + id + "'");
        Cursor cr = db.rawQuery(query, null);
        return cr;
    }

    public Integer DeleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }

    public Cursor ViewAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cr;
    }

    public void DeleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}
