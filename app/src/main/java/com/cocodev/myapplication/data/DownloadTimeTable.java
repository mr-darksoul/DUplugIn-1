package com.cocodev.myapplication.data;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Sudarshan on 11-06-2017.
 */

public class DownloadTimeTable extends AsyncTask<Void,Integer,Void> {


    @Override
    protected Void doInBackground(Void[] params) {
        HttpsURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File root = Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/KMV");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File timeTable = new File(dir.getPath() + "/TimeTable");
            if(!timeTable.exists()){
                timeTable.mkdir();
            }
        }

        try {

            URL url = new URL("https://server-1.000webhostapp.com/TimeTable/timeTable.csv");
            urlConnection = (HttpsURLConnection) url.openConnection();
            int lof = urlConnection.getContentLength();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                // Nothing to do.
                Log.e("his","inputStream was Null");
                return null;
            }

            DataInputStream dis = new DataInputStream(inputStream);

            byte[] buffer = new byte[1024];
            int length;

            FileOutputStream fos = new FileOutputStream(new File(
                    Environment.getExternalStorageDirectory() + "/" + "KMV/TimeTable/timeTable.csv")
            );
            long total =0;
            while ((length = dis.read(buffer))>0) {
                total+=length;
                publishProgress((int)(total/lof)*100);
                fos.write(buffer, 0, length);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //setProgressBar

    }
}
