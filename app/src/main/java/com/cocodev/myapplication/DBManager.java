package com.cocodev.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankush on 11/16/2016.
 */
public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kmv.db";

    //articles
    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ARTICLE_ID = "_id";
    public static final String COLUMN_ARTICLE_TITLE = "title";
    public static final String COLUMN_ARTICLE_SHORTDESC = "short_desc";
    public static final String COLUMN_ARTICLE_LONGDESC = "long_desc";
    public static final String COLUMN_ARTICLE_DISPLAYPICT = "displaypict";


    //notices
    public static final String TABLE_NOTICES = "articles";
    public static final String COLUMN_NOTICE_ID = "_id";
    public static final String COLUMN_NOTICE_TITLE = "title";
    public static final String COLUMN_NOTICE_LONGDESC = "long_desc";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE " + TABLE_ARTICLES + "(" +
                COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_ARTICLE_TITLE + " TEXT " +
                COLUMN_ARTICLE_SHORTDESC + " TEXT " +
                COLUMN_ARTICLE_LONGDESC + " TEXT " +
                COLUMN_ARTICLE_DISPLAYPICT + " TEXT " +
                ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
            onCreate(db);
    }

    //add article
    public void addArticle(String title,String shortdesc){


        ContentValues values = new ContentValues();
        values.put(COLUMN_ARTICLE_TITLE,title);
        values.put(COLUMN_ARTICLE_SHORTDESC,shortdesc);

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_ARTICLES, null, values);
        db.close();


    }

    public Cursor getallnotices(){
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = new String[] { COLUMN_ARTICLE_ID, COLUMN_ARTICLE_TITLE,COLUMN_ARTICLE_SHORTDESC,COLUMN_ARTICLE_LONGDESC,COLUMN_ARTICLE_DISPLAYPICT };

        Cursor cursor = db.query(TABLE_ARTICLES, columns, null,
                null, null, null, null);

        return cursor;
    }



}
