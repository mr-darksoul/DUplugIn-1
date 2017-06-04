package com.cocodev.myapplication.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sudarshan on 01-06-2017.
 */

public class FetchDataTask extends AsyncTask<Void, Void, Void> {

    Context mContext;
    public FetchDataTask(Context context) {
        super();
        mContext=context;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(mContext,"onPostExecute FetchDataTask",Toast.LENGTH_SHORT).show();

    }

    long addDepartment(String name, String incharge){
        long departmentID;
        Cursor departmentCursor = mContext.getContentResolver().query(
                Contract.DepartmentEntry.CONTENT_URI,
                new String[] {Contract.DepartmentEntry._ID},
                Contract.DepartmentEntry.COLUMN_DEPARTMENT_NAME + " =? ",
                new String[] {name},
                null);

        if(departmentCursor.moveToFirst()){
            int locationIdIndex = departmentCursor.getColumnIndex(Contract.DepartmentEntry._ID);
            departmentID = departmentCursor.getLong(locationIdIndex);
        }else{
            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.DepartmentEntry.COLUMN_DEPARTMENT_NAME,name);
            contentValues.put(Contract.DepartmentEntry.COLUMN_TEACHER_INCHARGE,incharge);
            Uri insertedUri ;
            insertedUri = mContext.getContentResolver().insert(Contract.DepartmentEntry.CONTENT_URI,contentValues);

            departmentID = ContentUris.parseId(insertedUri);
        }

        return departmentID;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String LOG_TAG = "log_tag";
        Log.d(LOG_TAG, "Starting sync");

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String noticeString = null;


        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "https://server-1.000webhostapp.com/?";
            final String QUERY_PARAM = "type";



            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, "notice")
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            noticeString = buffer.toString();
            getNoticesFromJSON(noticeString);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
        }catch (JSONException e) {
           Log.e(LOG_TAG, e.getMessage(), e);
           e.printStackTrace();}
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }

    @Nullable
    private Void[] getNoticesFromJSON(String noticeJsonString)throws JSONException{
        try{

            JSONObject noticeJSON = new JSONObject(noticeJsonString);

            JSONArray noticeArray = noticeJSON.getJSONArray("list");

            for(int i=0; i <noticeArray.length();i++){
                JSONObject notice = noticeArray.getJSONObject(i);
                String id = notice.getString("id");
                String time = notice.getString("time");
                String deadline = notice.getString("deadline");
                String description = notice.getString("description");
                String name = notice.getString("name");
                String incharge = notice.getString("incharge");

                long departmentID = addDepartment(name,incharge);
                ContentValues contentValues = new ContentValues();
                contentValues.put(Contract.NoticeEntry.COLUMN_TIME,time);
                contentValues.put(Contract.NoticeEntry.COLUMN_DEADLINE,deadline);
                contentValues.put(Contract.NoticeEntry.COLUMN_DEPARTMENT,departmentID);
                contentValues.put(Contract.NoticeEntry.COLUMN_DESCRIPTION,description);

                Uri uri = mContext.getContentResolver().insert(Contract.NoticeEntry.CONTENT_URI,contentValues);
                long id_insertedNOTICE = ContentUris.parseId(uri);

            }

        } catch (JSONException e) {
            Log.e("TAG", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
