package com.cocodev.myapplication.notices;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.myapplication.MainActivity;
import com.cocodev.myapplication.MyApplication;
import com.cocodev.myapplication.R;
import com.cocodev.myapplication.Utility.Notice;
import com.cocodev.myapplication.adapter.CustomNoticeCursorAdapter;
import com.cocodev.myapplication.data.Contract;
import com.cocodev.myapplication.data.FetchArticleTask;
import com.cocodev.myapplication.data.FetchDataTask;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Notices extends Fragment  {

    public static final String key = "type";
    public static final int TYPE_ALL = 0;
    public static final int TYPE_CLASS = 1;
    public static final int TYPE_COLLEGE = 2;
    public int type=-1;
    private View mView;
    private final int NOTICE_LOADER =1;
    private ListView mListView;
    CustomNoticeCursorAdapter mSimpleCursorAdapter;
    Cursor mCursor;


    public Notices() {}

    public static final Notices newInstance(int type){
        Notices n = new Notices();
        n.setType(type);
        return n;
    }
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseListAdapter mAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(savedInstanceState!=null){
                type = savedInstanceState.getInt(key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView  = inflater.inflate(R.layout.fragment_notices, container, false);
        TextView textView = (TextView) mView.findViewById(R.id.notices_emptyView);
        textView.setText("There are currently no Notices under this Category.");
        mListView = (ListView) mView.findViewById(R.id.list_notices);
        mListView.setEmptyView(textView);
        reference = firebaseDatabase.getReference().child("Categories").child("Notices")
                .child(getTypeString());

        mAdapter = new FirebaseListAdapter<String>(
                getActivity(),
                String.class,
                R.layout.adapter_notice,
                reference
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                final TextView description = (TextView) v.findViewById(R.id.notice_description);
                final TextView time = (TextView) v.findViewById(R.id.notice_time);
                final TextView deadline = (TextView) v.findViewById(R.id.notice_deadline);

                DatabaseReference dbref = firebaseDatabase.getReference().child("Notices")
                        .child(model);
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Notice notice = dataSnapshot.getValue(Notice.class);
                        deadline.setText(notice.getDeadline());
                        time.setText(notice.getTime());
                        description.setText(notice.getDescription());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("TAG", "onCancelled --> addValueEventListener --> populateView" + databaseError.toString());
                    }
                });
            }
        };
        mListView.setAdapter(mAdapter);
        return mView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setType(int type){
        this.type=type;
    }

    public String getTypeString(){
        switch(type){
            case TYPE_ALL:
                return "All";
            case TYPE_CLASS:
                return "Class";
            case TYPE_COLLEGE:
                return "College";
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(key,type);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mAdapter!=null) {
            mAdapter.cleanup();
        }
    }
}
