package com.cocodev.TheDuChronicle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cocodev.TheDuChronicle.Utility.Article;
import com.cocodev.TheDuChronicle.Utility.PushComment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentBox extends AppCompatActivity {
    private static String INITIAL_LIKES = "0";
    private static String INITIAL_DISLIKES = "0";
    EditText mCommentField;
    Button mSubmitButton;
    String uid;
    DatabaseReference databaseReference;
    String commentKey;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_comment_box);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Comment Here");
        actionBar.setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("comments");
        Intent intent = getIntent();
        uid = intent.getStringExtra("postUID");
        mCommentField = (EditText)findViewById(R.id.editText_comment);
        mSubmitButton = (Button) findViewById(R.id.button_submit_comment);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = mCommentField.getText().toString();
                String time = getCurrentTime();
                PushComment pushComment = new PushComment(uid,time,comment,INITIAL_LIKES,INITIAL_DISLIKES);
                commentKey = databaseReference.push().getKey();
                databaseReference.child(uid).child("comment").child(commentKey).setValue(pushComment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Comment Submitted",Toast.LENGTH_SHORT).show();

                    }
                });
                databaseReference.child(uid).child("comment").child(commentKey).child("likes").setValue("uID");
                databaseReference.child(uid).child("comment").child(commentKey).child("disLikes").setValue("uID");
            }
        });
    }
    private static String getCurrentTime()
    {
        Long time = System.currentTimeMillis();
        String ts = time.toString();
        return  ts;
    }
}
