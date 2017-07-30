package com.cocodev.university.delhi.duplugin;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.university.delhi.duplugin.Utility.Notice;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.cocodev.university.delhi.duplugin.Utility.Utility.getTimeAgo;

public class NoticeDetails extends AppCompatActivity {
    public static final String key = "notice_uid";
    String noticeUid;

    boolean check=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);
        initActionBar();
        Intent intent = getIntent();

        noticeUid = intent.getStringExtra(key);

        final TextView title = (TextView) findViewById(R.id.notice_details_title);
        final TextView description = (TextView) findViewById(R.id.notice_details_description);
        final TextView time = (TextView) findViewById(R.id.notice_deatails_time);
        final TextView deadline = (TextView) findViewById(R.id.notice_details_deadline);
        final TextView uid = (TextView) findViewById(R.id.notice_uid);
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Notices")
//                .child(noticeUid);
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference()
                .child("College Content")
                .child(PreferenceManager.getDefaultSharedPreferences(this).getString(SA.KEY_COLLEGE,null))
                .child("Notices")
                .child(noticeUid);
        reference2.keepSynced(true);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notice notice = dataSnapshot.getValue(Notice.class);
                if(notice==null) {
                    if(check){
                        check=false;
                    }else{
                        Toast.makeText(NoticeDetails.this, "This Notice has been deleted.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    return;
                }
                title.setText(Html.fromHtml(notice.getTitle()));
                description.setText(Html.fromHtml(notice.getDescription()));
                time.setText(Html.fromHtml(getTimeAgo(NoticeDetails.this,notice.getTime())));
                deadline.setText(getTimeAgo(NoticeDetails.this,notice.getDeadline()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //
            }
        };
        //reference.addListenerForSingleValueEvent(valueEventListener);
        reference2.addListenerForSingleValueEvent(valueEventListener);

    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(isTaskRoot()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();

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
