package com.cocodev.TheDuChronicle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.TheDuChronicle.Utility.Article;
import com.cocodev.TheDuChronicle.Utility.Comment;
import com.cocodev.TheDuChronicle.adapter.CommentAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Article_details extends AppCompatActivity implements AbsListView.OnScrollListener{
    private int preLast =0;
    private Article article;
    public static final String key = "article";
    Context context = this;
    //LayoutInflater layoutInflater = this.getLayoutInflater();
    //Add listview
    ListView mListView;
    View mFooterView;
    View mFooterButton;
    CommentAdapter commentAdapter;
    final static List<Comment> mCommentEntries = new ArrayList<>();
    Button postButton;

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
        String UID = intent.getStringExtra(key);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        final TextView titleView= (TextView) findViewById(R.id.article_title);;
        final TextView timeView= (TextView) findViewById(R.id.article_time);;
        final TextView authorView= (TextView) findViewById(R.id.article_author);;
        final TextView descriptionView= (TextView) findViewById(R.id.article_description);


        mListView = (ListView) findViewById(R.id.article_list_view);
        commentAdapter = new CommentAdapter(this,R.layout.review_single_list,mCommentEntries);
        //View headerView = (View) layoutInflater.inflate(R.layout.review_list, mListView, false);
        View view = LayoutInflater.from(context).inflate(R.layout.review_list,null);
        commentAdapter.add(new Comment("CoCo developer","Review 1"));
        commentAdapter.add(new Comment("Shudarshan Yadav","Review 2"));
        commentAdapter.add(new Comment("Manav Bansal","Review 3"));
        mListView.setAdapter(commentAdapter);
        mListView.addHeaderView(view);

        mFooterView = LayoutInflater.from(context).inflate(R.layout.footer_progress_bar, null);

        mListView.addFooterView(mFooterView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mListView==null)
                    return;
                mListView.removeFooterView(mFooterView);
                commentAdapter.notifyDataSetChanged();
            }
        },5000);
         mFooterButton = LayoutInflater.from(context).inflate(R.layout.button_post,null);
         mListView.addFooterView(mFooterButton);
        commentAdapter.notifyDataSetChanged();
        postButton = (Button) findViewById(R.id.button_post_review);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Post Button Clicked!", Toast.LENGTH_SHORT).show();
                //Launch Review Activity
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.articleImage);


        DatabaseReference reference =FirebaseDatabase.getInstance().getReference().child("Articles")
                .child(UID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                article = dataSnapshot.getValue(Article.class);
                //PRODUCING ERROR!!!
                //java.lang.NullPointerException: Attempt to invoke virtual method 'void android.widget.TextView.setText(java.lang.CharSequence)' on a null object reference
                //timeView.setText(article.getTime());
                //titleView.setText(article.getTitle());
                //authorView.setText(article.getAuthor());
               // descriptionView.setText(article.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //
            }
        });


    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {


        //return super.onCreateView(parent, name, context, attrs);

        return super.onCreateView(parent, name, context, attrs);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        commentAdapter.clear();
        finish();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {



    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        final int lastItem = i + i1-mListView.getFooterViewsCount();

        if(lastItem == i2)
        {
            if(preLast!=lastItem) {
                Log.e("his",Integer.toString(preLast)+" "+Integer.toString(lastItem));
                if(mListView.getFooterViewsCount()==0){
                    mListView.addFooterView(mFooterView);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(mListView==null)
                                return;
                            mListView.removeFooterView(mFooterView);
                            commentAdapter.notifyDataSetChanged();
                        }
                    },5000);
                }
                preLast = lastItem;
                //to avoid multiple calls for last item
               // commentAdapter.populateMoreList(mListView,mFooterView);
            }
        }

    }
}
