package com.cocodev.university.delhi.duplugin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.university.delhi.duplugin.Utility.IntroManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class SplashScreen extends AppCompatActivity {
    private long animDuration = 800;
    private IntroManager session;
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final TextView D = (TextView) findViewById(R.id.D);
        final TextView U = (TextView) findViewById(R.id.U);

        final Animation slide_in_right = AnimationUtils.loadAnimation(this,R.anim.slide_in_right);
        slide_in_right.setDuration(animDuration);

        Animation slide_in_left = AnimationUtils.loadAnimation(this,R.anim.slide_in_left);
        slide_in_left.setDuration(animDuration);

        final Animation slide_out_left = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.slide_out_left);
        slide_out_left.setDuration(animDuration-700);

        final Animation slide_out_right = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.slide_out_right);
        slide_out_right.setDuration(animDuration-700);

        slide_out_left.setStartOffset(200);
        slide_out_right.setStartOffset(200);

        slide_in_right.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                D.startAnimation(slide_out_left);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slide_in_left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                U.startAnimation(slide_out_right);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        D.startAnimation(slide_in_right);
        U.startAnimation(slide_in_left);
//        try {
//            sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


//
//

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
                    sleep(1050);
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
