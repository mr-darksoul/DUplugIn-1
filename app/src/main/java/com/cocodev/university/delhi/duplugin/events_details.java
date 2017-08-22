package com.cocodev.university.delhi.duplugin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.university.delhi.duplugin.Utility.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.cocodev.university.delhi.duplugin.Utility.Utility.getTimeAgo;

public class events_details extends AppCompatActivity {
    public static String key = "uid";
    private String Uid = "";
    boolean check = true;
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
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference()
                .child("College Content")
                .child(PreferenceManager.getDefaultSharedPreferences(events_details.this).getString(SA.KEY_COLLEGE,null))
                .child("Events")
                .child(Uid);
        reference2.keepSynced(true);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                if(event==null) {
                    if(check){
                        check=false;
                    }else{
                        Toast.makeText(events_details.this, "This Article has been deleted.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    return;
                }
                timeView.setText(getTimeAgo(events_details.this,event.getDate()));
                titleView.setText(event.getTitle());
                eventPlace.setText(event.getVenue());
                descriptionView.setText(fromHtml(event.getDescription()));
                if(!event.getUrl().equals("")) {
                    Picasso.with(events_details.this).load(event.getUrl()).placeholder(R.drawable.event_place_holder).fit().centerInside().into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addListenerForSingleValueEvent(valueEventListener);
        reference2.addListenerForSingleValueEvent(valueEventListener);
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

    @Override
    public void onBackPressed() {
        if(isTaskRoot()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
