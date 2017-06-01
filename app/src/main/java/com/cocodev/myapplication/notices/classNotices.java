package com.cocodev.myapplication.notices;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cocodev.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class classNotices extends Fragment {
    ArrayAdapter<String> adapter;

    public classNotices() {
        // Required empty public constructor
    }


     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_notices, container, false);


        List<String> classNotices = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getActivity(),R.layout.adapter_notice,R.id.noticeText,classNotices);
        ListView listViewClass = (ListView) view.findViewById(R.id.listview_classnotices);
        listViewClass.setAdapter(adapter);
        FetchDataTask fetchDataTask = new FetchDataTask();
        fetchDataTask.execute();
        return view;
    }

    public class FetchDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected void onPostExecute(String[] strings) {
            adapter.addAll(strings);
        }

        @Override
        protected String[] doInBackground(Void... params) {
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
                return getNoticesFromJSON(noticeString);
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

        private String[] getNoticesFromJSON(String noticeJsonString)throws JSONException{
            try{

                JSONObject noticeJSON = new JSONObject(noticeJsonString);

                JSONArray noticeArray = noticeJSON.getJSONArray("list");
                String[] strings = new String[noticeArray.length()];
                for(int i=0; i <noticeArray.length();i++){
                    JSONObject notice = noticeArray.getJSONObject(i);
                    String id = notice.getString("id");
                    String department = notice.getString("department");
                    String time = notice.getString("time");
                    String deadline = notice.getString("deadline");
                    String description = notice.getString("description");
                    strings[i] = "id: " + id + " time:" + time + " description: " +description + " deadline: " + deadline;
                    Log.e("his",strings[i]);
                }
                return strings;
            } catch (JSONException e) {
                Log.e("TAG", e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
    }

}
