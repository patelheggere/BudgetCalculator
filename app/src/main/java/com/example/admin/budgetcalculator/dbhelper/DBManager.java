package com.example.admin.budgetcalculator.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.budgetcalculator.model.DetailsModel;

public class DBManager {
    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(DetailsModel object) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.DETAILS, object.getDetails());
        contentValue.put(DatabaseHelper.DEBIT_CREDIT, object.getDebit_credit());
        contentValue.put(DatabaseHelper.DATE, object.getDate());
        contentValue.put(DatabaseHelper.REMARKS, object.getRemarks());
        contentValue.put(DatabaseHelper.MONTH, object.getMonth());
        contentValue.put(DatabaseHelper.YEAR, object.getYear());
        System.out.println("Inserted 1:"+database.insert(DatabaseHelper.TABLE_NAME, null, contentValue));
    }

    public Cursor fetch(String month) {
        String[] columns = new String[] { DatabaseHelper._ID };
        Cursor cursor;// = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_NAME +" WHERE "+DatabaseHelper.MONTH +"="+month, null);
        if (cursor != null) {
            cursor.moveToFirst();
            //Log.d("", "fetch: "+cursor.getString(cursor.getColumnIndex(DatabaseHelper.MONTH)));
        }
        return cursor;
    }
    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID };
        Cursor cursor;// = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_NAME , null);
        if (cursor != null) {
            cursor.moveToFirst();
            //Log.d("", "fetch: "+cursor.getString(cursor.getColumnIndex(DatabaseHelper.MONTH)));
        }
        return cursor;
    }
/*
    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }*/

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }


    public void insert2(DetailsModel object) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.DETAILS, object.getDetails());
        contentValue.put(DatabaseHelper.DEBIT_CREDIT, object.getDebit_credit());
        contentValue.put(DatabaseHelper.DATE, object.getDate());
        contentValue.put(DatabaseHelper.REMARKS, object.getRemarks());
        contentValue.put(DatabaseHelper.MONTH, object.getMonth());
        contentValue.put(DatabaseHelper.YEAR, object.getYear());
        System.out.println("Inserted 2:"+database.insert(DatabaseHelper.TABLE_NAME2, null, contentValue));
    }

    public Cursor fetch2(String mon) {
        System.out.println("cbbnb");
        String[] columns = new String[] { DatabaseHelper._ID };
        Cursor cursor;// = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor = database.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_NAME2 +" WHERE "+DatabaseHelper.MONTH +"="+mon, null);
        if (cursor != null) {
            Log.d("", "fetch2: ");
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String month, String day) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MONTH, month);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public int update(long _id, String debit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DEBIT_CREDIT, debit);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }
    public int update2(long _id, String debit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DEBIT_CREDIT, debit);
        int i = database.update(DatabaseHelper.TABLE_NAME2, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public int update(long _id, String month, String day, String year, String amout, String remarks, String debit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MONTH, month);
        contentValues.put(DatabaseHelper.DETAILS, amout);
        contentValues.put(DatabaseHelper.YEAR, year);
        contentValues.put(DatabaseHelper.DATE, day);
        contentValues.put(DatabaseHelper.REMARKS, remarks);
        contentValues.put(DatabaseHelper.DEBIT_CREDIT, debit);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }
    public int update2(long _id, String month, String day, String year, String amout, String remarks, String debit) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MONTH, month);
        contentValues.put(DatabaseHelper.DETAILS, amout);
        contentValues.put(DatabaseHelper.YEAR, year);
        contentValues.put(DatabaseHelper.DATE, day);
        contentValues.put(DatabaseHelper.REMARKS, remarks);
        contentValues.put(DatabaseHelper.DEBIT_CREDIT, debit);
        int i = database.update(DatabaseHelper.TABLE_NAME2, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }
    public int update2(long _id, String month, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.MONTH, month);
        int i = database.update(DatabaseHelper.TABLE_NAME2, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public int updateAmount(long _id, String month, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DETAILS, month);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }
    public int update2Amount(long _id, String month, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.DETAILS_2, month);
        int i = database.update(DatabaseHelper.TABLE_NAME2, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete2(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME2, DatabaseHelper._ID + "=" + _id, null);
    }
}