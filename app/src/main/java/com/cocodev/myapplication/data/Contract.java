
package com.cocodev.myapplication.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class Contract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.cocodev.myapplication.MainActivity";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_ARTICLE = "article";
    public static final String PATH_NOTICE= "notice";
    public static final String PATH_DEPARTMENT= "department";


    /* Inner class that defines the table contents of the location table */
    public static final class DepartmentEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DEPARTMENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEPARTMENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DEPARTMENT;

        // Table name
        public static final String TABLE_NAME = "department";

        public static final String COLUMN_DEPARTMENT_NAME = "department_name";
        public static final String COLUMN_TEACHER_INCHARGE = "teacher_incharge";


        public static Uri buildDepartmentUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class NoticeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NOTICE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTICE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NOTICE;

        public static final String TABLE_NAME = "notice";

        // column for the title of the article
        public static final String COLUMN_DEPARTMENT = "department";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DEADLINE = "deadline";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_CHECK = "complete";




        public static Uri buildNoticeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /*
            Student: This is the buildArticleDepartment function you filled in.
         */
        public static Uri buildNoticeDepartment(String departmentSetting) {
            return CONTENT_URI.buildUpon().appendPath(departmentSetting).build();
        }

        public static Uri buildNoticeDepartmentWithStartTime(
                String locationSetting, long startDate) {
            long normalizedDate = /*normalizeDate(*/startDate;
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_TIME, Long.toString(normalizedDate)).build();
        }


        public static String getDepartmentSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }


        public static String getStartTimeFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_TIME);
            if (null != dateString && dateString.length() > 0)
                return dateString;
            else
                return null;
        }
    }

    /* Inner class that defines the table contents of the weather table */
    public static final class ArticleEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLE;

        public static final String TABLE_NAME = "article";

        // column for the title of the article
        public static final String COLUMN_TITLE = "title";

        // column for the tagline of the article
        public static final String COLUMN_TAG_LINE = "tagline";


        // DateTIME
        public static final String COLUMN_TIME = "time";


        public static final String COLUMN_SHORT_DESC = "shortdesc";
        public static final String COLUMN_LONG_DESC = "longdesc";

        // author department image
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DEPARTMENT = "department";
        public static final String COLUMN_IMAGE = "image";



        public static Uri buildWeatherUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        /*
            Student: This is the buildArticleDepartment function you filled in.
         */
        public static Uri buildArticleDepartment(String departmentSetting) {
            return CONTENT_URI.buildUpon().appendPath(departmentSetting).build();
        }

        public static Uri buildArticledDepartmenWithStartTime(
                String locationSetting, long startDate) {
            long normalizedDate = /*normalizeDate(*/startDate;
            return CONTENT_URI.buildUpon().appendPath(locationSetting)
                    .appendQueryParameter(COLUMN_TIME, Long.toString(normalizedDate)).build();
        }

        public static Uri buildArticleDepartmentWithTime(String departmentSetting, long date) {
            return CONTENT_URI.buildUpon().appendPath(departmentSetting)
                    .appendPath(Long.toString(/*normalizeDate(*/date)).build();
        }

        public static String getDepartmentSettingFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static long getStartTimeFromUri(Uri uri) {
            String dateString = uri.getQueryParameter(COLUMN_TIME);
            if (null != dateString && dateString.length() > 0)
                return Long.parseLong(dateString);
            else
                return 0;
        }
    }
}
