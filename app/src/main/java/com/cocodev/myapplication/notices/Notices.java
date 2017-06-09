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
import com.cocodev.myapplication.adapter.CustomNoticeCursorAdapter;
import com.cocodev.myapplication.data.Contract;
import com.cocodev.myapplication.data.FetchArticleTask;
import com.cocodev.myapplication.data.FetchDataTask;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Notices extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView  = inflater.inflate(R.layout.fragment_notices, container, false);

        mListView = (ListView) mView.findViewById(R.id.list_notices);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchDataTask fetchDataTask = new FetchDataTask(getContext(),swipeRefreshLayout);
                fetchDataTask.execute();
            }
        });

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(NOTICE_LOADER,null,this);

        Cursor cursor = getContext().getContentResolver().query(
                Contract.NoticeEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        mSimpleCursorAdapter =new CustomNoticeCursorAdapter(getContext(),cursor,false);

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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        return new CursorLoader(getActivity(),
                Contract.NoticeEntry.CONTENT_URI,
                null,
                null,
                null,
                Contract.NoticeEntry.COLUMN_CHECK);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(mCursor==null){
            mCursor= data;
            mSimpleCursorAdapter  = new CustomNoticeCursorAdapter(getActivity(),mCursor,false);
            mListView.setAdapter(mSimpleCursorAdapter);
        }else{
            mSimpleCursorAdapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSimpleCursorAdapter.swapCursor(null);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(NOTICE_LOADER);
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
