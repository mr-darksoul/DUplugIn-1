// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.
package com.cocodev.myapplication.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";


    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    //articles
    public static final String TABLE_ARTICLES = "articles";
    public static final String ARTICLE_ID = "_id";
    public static final String ARTICLE_TITLE = "title";
    public static final String ARTICLE_LONGDESC = "long_desc";
    public static final String ARTICLE_DISPLAYPICT = "displaypict";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_NAME = 1;
    public static final int COL_STUDENTNUM = 2;
    public static final int COL_FAVCOLOUR = 3;
    //articles
    public static final String COLUMN_ARTICLE_ID = "0";
    public static final String COLUMN_ARTICLE_TITLE = "1";
    public static final String COLUMN_ARTICLE_LONGDESC = "2";
    public static final String COLUMN_ARTICLE_DISPLAYPICT = "3";


    public static final String[] ALL_KEYS = new String[] {ARTICLE_ID, ARTICLE_TITLE, ARTICLE_LONGDESC, ARTICLE_DISPLAYPICT};

    // DB info: it's name, and the table we are using (just one).
    private static int DATABASE_VERSION = 1; // Track DB version if a new version of your app changes the format.
    private static final String DATABASE_NAME = "kmv.db";
    public static final String DATABASE_TABLE = "articles";


    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + ARTICLE_ID + " integer primary key autoincrement, "
			
			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + ARTICLE_TITLE + " text not null, "
                    + ARTICLE_LONGDESC + " string not null, "
                    + ARTICLE_DISPLAYPICT + " string "

                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String title, String longdesc  ) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(ARTICLE_TITLE, title);
        initialValues.put(ARTICLE_LONGDESC, longdesc);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = ARTICLE_ID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(ARTICLE_ID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = ARTICLE_ID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, String title, String longdesc ) {
        String where = ARTICLE_ID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(ARTICLE_TITLE, title);
        newValues.put(ARTICLE_LONGDESC, longdesc);


        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;

    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
