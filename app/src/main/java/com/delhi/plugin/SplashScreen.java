package com.cocodev.duplugin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cocodev.duplugin.Utility.IntroManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class SplashScreen extends AppCompatActivity {
    private IntroManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new IntroManager(this);
        Thread myThread = new Thread()
        {
            @Override
            public void run()
            {
                try {
                    DatabaseReference hpDatabaseReference = FirebaseDatabase.getInstance().getReference().child("CategoryList").child("Articles");
                    hpDatabaseReference.addListenerForSingleValueEvent(hpValueEventListener);
                    DatabaseReference epDatabaseReference = FirebaseDatabase.getInstance().getReference().child("CategoryList").child("Events");
                    epDatabaseReference.addListenerForSingleValueEvent(epValueEventListener);
                    sleep(1000);
                    //Check if application is launching first time or not
                    //If it is First Time Launch Show IntroSlider
                    if (session.isFirstTimeLaunch()) {
                        launchIntroScreen();
                        finish();
                    }
                    //else Show Main Content
                    else {
                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
                        finish();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        setContentView(R.layout.activity_splash_screen);
        myThread.start();

    }

    //Add Switches to Home Preferences
    ValueEventListener hpValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SharedPreferences sharedPreferences = getSharedPreferences(SA.fileName_HP,MODE_PRIVATE);
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

            while (iterator.hasNext()){
                DataSnapshot temp = iterator.next();

                if(!sharedPreferences.contains(temp.getKey())){
                    Toast.makeText(SplashScreen.this, "New Category Added", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(temp.getKey().toString(),true);
                    editor.commit();
                    Home.setHomePreferencesChanged(true);
                }

            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(SplashScreen.this,"There has been some problem connecting to the server.",Toast.LENGTH_SHORT).show();
        }
    };

    //add switches to Events Preferences
    ValueEventListener epValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            SharedPreferences sharedPreferences = getSharedPreferences(SA.fileName_EP,MODE_PRIVATE);
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

            while (iterator.hasNext()){
                DataSnapshot temp = iterator.next();
                if(!sharedPreferences.contains(temp.getKey())){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(temp.getKey().toString(),true);
                    editor.commit();
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(SplashScreen.this,"There has been some problem connecting to the server.",Toast.LENGTH_SHORT).show();
        }
    };
    //Launch InroSlider Activity
    private void launchIntroScreen() {
        session.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashScreen.this, IntroSlider.class));
        finish();
    }
}
