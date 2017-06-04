package com.cocodev.myapplication.data.db;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.cocodev.myapplication.data.Contract;
import com.cocodev.myapplication.notices.Notices;

/**
 * Created by Sudarshan on 02-06-2017.
 */

public class ContentProvider extends android.content.ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DBHelper mDBHelper;

    static final int ARTICLE = 100;
    static final int ARTICLE_WITH_DEPARTMENT = 101;
    static final int NOTICE = 200;
    static final int NOTICE_WITH_DEPARTMENT = 201;
    static final int NOTICE_WITH_DEPARTMENT_AND_START_TIME = 202;
    static final int DEPARTMENT = 300;

    private static final SQLiteQueryBuilder sArticleByDepartmentQueryBuilder;

    static{
        sArticleByDepartmentQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sArticleByDepartmentQueryBuilder.setTables(
                Contract.ArticleEntry.TABLE_NAME + " INNER JOIN " +
                        Contract.DepartmentEntry.TABLE_NAME +
                        " ON " + Contract.ArticleEntry.TABLE_NAME +
                        "." + Contract.ArticleEntry.COLUMN_DEPARTMENT +
                        " = " + Contract.DepartmentEntry.TABLE_NAME +
                        "." + Contract.DepartmentEntry._ID);
    }

    private static final SQLiteQueryBuilder sNoticeByDepartmentQueryBuilder;

    static {
        sNoticeByDepartmentQueryBuilder = new SQLiteQueryBuilder();

        //inner join
        sNoticeByDepartmentQueryBuilder.setTables(
                Contract.NoticeEntry.TABLE_NAME + " INNER JOIN " +
                        Contract.DepartmentEntry.TABLE_NAME +
                        " ON " + Contract.NoticeEntry.TABLE_NAME +
                        "." + Contract.NoticeEntry.COLUMN_DEPARTMENT +
                        " = " + Contract.DepartmentEntry.TABLE_NAME +
                        "." + Contract.DepartmentEntry._ID
        );
    }


    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.PATH_ARTICLE, ARTICLE);
        matcher.addURI(authority, Contract.PATH_ARTICLE + "/*", ARTICLE_WITH_DEPARTMENT);
        matcher.addURI(authority, Contract.PATH_NOTICE, NOTICE);
        matcher.addURI(authority, Contract.PATH_NOTICE + "/*", NOTICE_WITH_DEPARTMENT);
        matcher.addURI(authority, Contract.PATH_NOTICE + "/*/#", NOTICE_WITH_DEPARTMENT_AND_START_TIME);

        matcher.addURI(authority, Contract.PATH_DEPARTMENT, DEPARTMENT);
        return matcher;
    }

    //location.location_setting = ?
    private static final String sDepartmentSettingSelection =
            Contract.DepartmentEntry.TABLE_NAME+
                    "." + Contract.DepartmentEntry._ID + " = ? ";

    //location.location_setting = ? AND startTime = ?
    private static final String sNoticeSettingWithStartTimeSelection =
            Contract.DepartmentEntry.TABLE_NAME+
                    "." + Contract.DepartmentEntry._ID + " = ? AND " +
            Contract.NoticeEntry.COLUMN_DEADLINE + " >= ? ";
    private static final String sNoticeSettingSelection =
            Contract.DepartmentEntry.TABLE_NAME+
                    "." + Contract.DepartmentEntry._ID + " = ? ";


    @Nullable
    private Cursor getArticleWithDepartment(Uri uri, String[] projection, String sortOrder){
        String department = Contract.ArticleEntry.getDepartmentSettingFromUri(uri);
        Log.e("his",department);
        String selection;
        String selectionArgs[];

        selection = sDepartmentSettingSelection;
        selectionArgs = new String[]{department};

        return sArticleByDepartmentQueryBuilder.query(mDBHelper.getReadableDatabase(),
                projection,
                null,
                null,
                null,
                null,
                sortOrder);

    }

    private Cursor getNoticeWithDepartmentAndStartTime(Uri uri, String[] projection, String sortOrder){
        String department = Contract.NoticeEntry.getDepartmentSettingFromUri(uri);
        String startTime = Contract.NoticeEntry.getStartTimeFromUri(uri);
        String selection;
        String selectionArgs[];

        selection = sNoticeSettingWithStartTimeSelection;
        selectionArgs = new String[]{department,startTime};

        return sNoticeByDepartmentQueryBuilder.query(mDBHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

    }

    private Cursor getNoticeWithDepartment(Uri uri, String[] projection, String sortOrder){
        String department = Contract.NoticeEntry.getDepartmentSettingFromUri(uri);
        String selection;
        String selectionArgs[];

        selection = sNoticeSettingSelection;
        selectionArgs = new String[]{department};

        return sNoticeByDepartmentQueryBuilder.query(mDBHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor= null;

        switch (sUriMatcher.match(uri)) {

            // "article/*"
            case ARTICLE_WITH_DEPARTMENT: {
                retCursor = getArticleWithDepartment(uri, projection, sortOrder);
                break;
            }
            // "article"
            case ARTICLE: {

                retCursor = mDBHelper.getReadableDatabase().query(
                        Contract.ArticleEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            // notice/*/#
            case NOTICE_WITH_DEPARTMENT_AND_START_TIME: {
                retCursor = getNoticeWithDepartmentAndStartTime(uri,projection,sortOrder);
                break;
            }

            case NOTICE_WITH_DEPARTMENT: {

                retCursor = getNoticeWithDepartment(uri,projection,sortOrder);
                break;
            }

            // "Notice"
            case NOTICE: {
                retCursor = mDBHelper.getReadableDatabase().query(
                        Contract.NoticeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            // "department"
            case DEPARTMENT: {
                retCursor = mDBHelper.getReadableDatabase().query(
                        Contract.DepartmentEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Student: Uncomment and fill out these two cases
            case ARTICLE_WITH_DEPARTMENT:
                return Contract.ArticleEntry.CONTENT_TYPE;
            case ARTICLE:
                return Contract.ArticleEntry.CONTENT_TYPE;
            case NOTICE_WITH_DEPARTMENT_AND_START_TIME:
                return Contract.NoticeEntry.CONTENT_TYPE;
            case NOTICE_WITH_DEPARTMENT:
                return Contract.NoticeEntry.CONTENT_TYPE;
            case NOTICE:
                return Contract.NoticeEntry.CONTENT_TYPE;
            case DEPARTMENT:
                return Contract.DepartmentEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch (match) {
            case ARTICLE: {
                //normalizeDate(values);
                long _id = db.insert(Contract.ArticleEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.ArticleEntry.buildWeatherUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case NOTICE: {
                long _id = db.insert(Contract.NoticeEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.NoticeEntry.buildNoticeUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case DEPARTMENT:{
                long _id = db.insert(Contract.DepartmentEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = Contract.DepartmentEntry.buildDepartmentUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case ARTICLE: {
                rowsDeleted = db.delete(
                        Contract.ArticleEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case NOTICE: {
                rowsDeleted = db.delete(
                        Contract.NoticeEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case DEPARTMENT:{
                rowsDeleted = db.delete(
                        Contract.DepartmentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case ARTICLE: {
                rowsUpdated = db.update(
                        Contract.ArticleEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }
            case NOTICE: {
                rowsUpdated = db.update(
                        Contract.NoticeEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }
            case DEPARTMENT:{
                rowsUpdated = db.update(
                        Contract.DepartmentEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount;
        switch (match) {
            case ARTICLE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(Contract.ArticleEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case NOTICE:{
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(Contract.NoticeEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public void shutdown() {
        mDBHelper.close();
        super.shutdown();
    }
}

