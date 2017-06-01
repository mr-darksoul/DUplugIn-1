package com.cocodev.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class categoryDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)){

            TextView textView = (TextView) findViewById(R.id.title_category);
            String message = intent.getStringExtra(Intent.EXTRA_TEXT);

            textView.setText(message);

        }


    }




}
