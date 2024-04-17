package com.example.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.style.IconMarginSpan;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "student.db";
    public static final String TABLE_NAME = "student_info";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String  COL3 = "BRANCH";
    public static final String  COL4 = "MARKS";
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY, NAME TEXT, BRANCH TEXT, MARKS INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String i, String n, String b, int m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, i);
        contentValues.put(COL2, n);
        contentValues.put(COL3, b);
        contentValues.put(COL4, m);
        long res = db.insert(TABLE_NAME, null, contentValues);
        return res != -1;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String i, String n, String b, int m){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, i);
        contentValues.put(COL2, n);
        contentValues.put(COL3, b);
        contentValues.put(COL4, m);
        long res = db.update(TABLE_NAME, contentValues, "ID=?", new String[] {i});
        return res!=-1;
    }

    public Cursor getOneData(String id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID=?", new String[] {id});
        return res;
    }

    public int deleteData(String id){
        SQLiteDatabase db = getWritableDatabase();
        int res = db.delete(TABLE_NAME, "ID=?", new String[] {id});
        return res;
    }

    public Cursor getBranchData(String b){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE BRANCH=?", new String[] {b});
        return res;
    }
}
