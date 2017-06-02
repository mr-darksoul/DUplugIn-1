package com.cocodev.myapplication.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cocodev.myapplication.data.Contract;

/**
 * Created by Ankush on 11/16/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kmv.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_DEPARTMENT_TABLE = "CREATE TABLE " + Contract.DepartmentEntry.TABLE_NAME + " (" +
                Contract.DepartmentEntry._ID + " INTEGER PRIMARY KEY," +
                Contract.DepartmentEntry.COLUMN_DEPARTMENT_NAME + " TEXT UNIQUE NOT NULL, " +
                Contract.DepartmentEntry.COLUMN_TEACHER_INCHARGE + " TEXT " +
                " );";

        String SQL_CREATE_NOTICE_TABLE = "CREATE TABLE " + Contract.NoticeEntry.TABLE_NAME + " (" +
                Contract.NoticeEntry._ID + " INTEGER PRIMARY KEY," +
                Contract.NoticeEntry.COLUMN_DEPARTMENT + " INTEGER NOT NULL, " +
                Contract.NoticeEntry.COLUMN_TIME+ " TEXT NOT NULL," +
                Contract.NoticeEntry.COLUMN_DEADLINE + " TEXT NOT NULL, " +
                Contract.NoticeEntry.COLUMN_DESCRIPTION +" TEXT NOT NULL, " +
                Contract.NoticeEntry.COLUMN_CHECK + "INTEGER DEFAULT 0, " +

                " FOREIGN KEY (" + Contract.NoticeEntry.COLUMN_DEPARTMENT + ") REFERENCES " +
                Contract.DepartmentEntry.TABLE_NAME + " (" + Contract.DepartmentEntry._ID + ") " +

                " );";

        String SQL_CREATE_ARTICLE_TABLE = "CREATE TABLE " + Contract.ArticleEntry.TABLE_NAME + " (" +
                Contract.ArticleEntry._ID + " INTEGER PRIMARY KEY," +
                Contract.ArticleEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                Contract.ArticleEntry.COLUMN_TAG_LINE + " TEXT NOT NULL, " +
                Contract.ArticleEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                Contract.ArticleEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                Contract.ArticleEntry.COLUMN_LONG_DESC +  " TEXT NOT NULL, " +
                Contract.ArticleEntry.COLUMN_AUTHOR +     " TEXT NOT NULL, " +
                Contract.ArticleEntry.COLUMN_DEPARTMENT +  " INTEGER NOT NULL, " +
                Contract.ArticleEntry.COLUMN_IMAGE +      " TEXT, " +

                " FOREIGN KEY (" + Contract.ArticleEntry.COLUMN_DEPARTMENT + ") REFERENCES " +
                Contract.DepartmentEntry.TABLE_NAME + " (" + Contract.DepartmentEntry._ID + ") " +
                " );";

        db.execSQL(SQL_CREATE_DEPARTMENT_TABLE);
        db.execSQL(SQL_CREATE_NOTICE_TABLE);
        db.execSQL(SQL_CREATE_ARTICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Contract.ArticleEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Contract.NoticeEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Contract.DepartmentEntry.TABLE_NAME);
            onCreate(db);
    }





}
