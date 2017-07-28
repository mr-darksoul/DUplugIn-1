package com.cocodev.duplugin;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sudarshan on 07-06-2017.
 */

public class MyApplication extends Application {



    @Override public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
