package com.example.holding.data.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "holding.db";
    private static final int DATABASE_VERSION = 1;

    protected static final String TABLE_USER = "user";
    protected static final String COLUMN_ID_USER = "id";
    protected static final String COLUMN_LOGIN_USER = "login";
    protected static final String COLUMN_PASSWORD_USER = "password";
    protected static final String COLUMN_GMAIL_USER = "email";
    protected static final String COLUMN_NAME_USER = "name";

    protected static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_LOGIN_USER + " TEXT, " +
            COLUMN_PASSWORD_USER + " TEXT," +
            COLUMN_GMAIL_USER + " TEXT, " +
            COLUMN_NAME_USER + " TEXT) ";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public SQLiteDatabase getWritableDatabaseInstance() {
        return this.getWritableDatabase();
    }
}
