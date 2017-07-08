package com.cocodev.duplugin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocodev.duplugin.Utility.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.cocodev.duplugin.Utility.Utility.getTimeAgo;

public class events_details extends AppCompatActivity {

    private String Uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details);
        Intent intent = getIntent();
        Uid = intent.getStringExtra("uid");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ImageView imageView = (ImageView) findViewById(R.id.event_image);
        final TextView titleView = (TextView) findViewById(R.id.event_detail_title);
        final TextView timeView = (TextView) findViewById(R.id.event_detail_time);
        final TextView eventPlace = (TextView) findViewById(R.id.event_detail_place);
        final TextView descriptionView = (TextView) findViewById(R.id.event_detail_desc);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events")
                .child(Uid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);

                timeView.setText(getTimeAgo(events_details.this,event.getTime()));
                titleView.setText(event.getTitle());
                eventPlace.setText(event.getVenue());
                descriptionView.setText(Html.fromHtml(event.getDescription()));
                Picasso.with(events_details.this).load(event.getUrl()).placeholder(R.drawable.event_place_holder).fit().centerInside().into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
