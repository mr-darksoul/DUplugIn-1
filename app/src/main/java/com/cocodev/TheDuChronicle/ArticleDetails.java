package com.cocodev.TheDuChronicle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.TheDuChronicle.Utility.Article;
import com.cocodev.TheDuChronicle.Utility.Comment;
import com.cocodev.TheDuChronicle.Utility.RefListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ArticleDetails extends AppCompatActivity{
    private int preLast = 0;
    private Article article;
    private String articleUid;
    public static final String key = "article";


    ListView mListView;
    View mFooterView;
    private DatabaseReference mCommentRefrence;
    private RefListAdapter<Comment> commentAdapter;

    Button toggleShowHideComments;

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
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        Intent intent = getIntent();
        final String UID = intent.getStringExtra(key);
        articleUid=UID;
        initActionBar();

        final View headerView = LayoutInflater.from(this).inflate(R.layout.comment_list_header, null);
        mListView = (ListView) findViewById(R.id.articleComment_listView);
        mListView.addHeaderView(headerView);


        final ImageView imageView = (ImageView) findViewById(R.id.articleImage);
        final TextView titleView = (TextView) headerView.findViewById(R.id.article_title);
        final TextView timeView = (TextView) headerView.findViewById(R.id.article_time);
        final TextView authorView = (TextView) headerView.findViewById(R.id.article_author);
        final TextView descriptionView = (TextView) headerView.findViewById(R.id.article_description);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Articles")
                .child(UID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                article = dataSnapshot.getValue(Article.class);

                timeView.setText(article.getTime());
                titleView.setText(article.getTitle());
                authorView.setText(article.getAuthor());
                descriptionView.setText(article.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //
            }
        });

        mCommentRefrence = FirebaseDatabase.getInstance().getReference().child("comments").child(articleUid);
        commentAdapter = new RefListAdapter<Comment>(
                this,
                Comment.class,
                R.layout.comment_layout,
                new DatabaseReference[] {mCommentRefrence}
        ) {
            @Override
            protected void populateView(View v, Comment model, int position) {
                TextView comment = (TextView) v.findViewById(R.id.commentLayout_comment);
                Button like = (Button) v.findViewById(R.id.commentLayout_like);
                Button dislike = (Button) v.findViewById(R.id.commentLayout_dislike);
                TextView time = (TextView) v.findViewById(R.id.comment_timeStamp);
                comment.setText(model.getComment());
                time.setText(Long.toString(model.getTimestamp()));
            }


        };

        mListView.setAdapter(null);

        final FloatingActionButton postComment = (FloatingActionButton) headerView.findViewById(R.id.articleDetails_postComment);
        toggleShowHideComments = (Button) headerView.findViewById(R.id.articleDetails_toggleShowHideComments);
        final EditText comment = (EditText) headerView.findViewById(R.id.articleDetails_comment_EditText);
        final Button submitComment = (Button) headerView.findViewById(R.id.articleDetails_submitComment);
        toggleShowHideComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(postComment.getVisibility()==View.GONE) {
                    postComment.setVisibility(View.VISIBLE);
                    toggleShowHideComments.setText("Hide Comments");
                    mListView.setAdapter(commentAdapter);

                }else{
                    comment.setVisibility(View.GONE);
                    submitComment.setVisibility(View.GONE);
                    postComment.setImageDrawable(ContextCompat.getDrawable(ArticleDetails.this,android.R.drawable.ic_menu_edit));
                    postComment.setVisibility(View.GONE);
                    toggleShowHideComments.setText("Show Comments");
                    mListView.setAdapter(null);

                }
            }
        });

        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(comment.getVisibility()==View.GONE) {
                    comment.setVisibility(View.VISIBLE);
                    submitComment.setVisibility(View.VISIBLE);
                    postComment.setImageDrawable(ContextCompat.getDrawable(ArticleDetails.this, android.R.drawable.ic_menu_close_clear_cancel));
                }else{
                    comment.setVisibility(View.GONE);
                    submitComment.setVisibility(View.GONE);
                    postComment.setImageDrawable(ContextCompat.getDrawable(
                            ArticleDetails.this, android.R.drawable.ic_menu_edit));

                }
            }
        });

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comment.getText().equals("")){
                    Toast.makeText(ArticleDetails.this, "You can not submit an empty comment", Toast.LENGTH_SHORT).show();
                }else{
                    String commentUid = mCommentRefrence.push().getKey();
                    Comment temp = new Comment(
                            articleUid,
                            null,
                            null,
                            0,0,
                            System.currentTimeMillis(),
                            comment.getText().toString(),
                            commentUid
                            );
                    mCommentRefrence.child(commentUid).setValue(temp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ArticleDetails.this, "Your comment was submitted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ArticleDetails.this, "There is a problem establishing connection to the server.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    comment.setText("");
                    postComment.callOnClick();
                }

            }
        });


    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
