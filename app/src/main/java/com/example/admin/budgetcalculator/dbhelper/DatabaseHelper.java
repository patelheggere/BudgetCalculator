package com.example.admin.budgetcalculator.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "Budget";
    public static final String TABLE_NAME2 = "Budget2";

    // Table columns
    public static final String _ID = "_id";
    public static final String SL_NUMBER = "sl_no";
    public static final String DATE = "date";
    public static final String DETAILS = "details";
    public static final String DEBIT_CREDIT = "debt_credit";
    public static final String REMARKS = "remarks";
    public static final String MONTH = "month";
    public static final String YEAR = "year";

    public static final String _ID_2 = "_id";
    public static final String SL_NUMBER_2 = "sl_no";
    public static final String DATE_2 = "date";
    public static final String DETAILS_2 = "details";
    public static final String DEBIT_CREDIT_2 = "debt_credit";
    public static final String REMARKS_2 = "remarks";

    // Database Information
    static final String DB_NAME = "BUDGET.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SL_NUMBER + " TEXT , " + DATE + " TEXT ," + MONTH + " TEXT , "+ YEAR + " TEXT ,"+ DEBIT_CREDIT +" TEXT ,"
            +DETAILS +" TEXT , "+REMARKS+" TEXT"+");";

    private static final String CREATE_TABLE2 = "create table " + TABLE_NAME2 + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SL_NUMBER + " TEXT , " + DATE + " TEXT ," + MONTH + " TEXT , "+ YEAR + " TEXT ,"+ DEBIT_CREDIT +" TEXT ,"
            +DETAILS +" TEXT , "+REMARKS+" TEXT"+");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }
}
