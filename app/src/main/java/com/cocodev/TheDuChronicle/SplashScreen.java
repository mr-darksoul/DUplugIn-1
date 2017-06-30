package com.cocodev.TheDuChronicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cocodev.TheDuChronicle.Utility.IntroManager;

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
                    sleep(3000);
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
    //Launch InroSlider Activity
    private void launchIntroScreen() {
        session.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashScreen.this, IntroSlider.class));
        finish();
    }
}
