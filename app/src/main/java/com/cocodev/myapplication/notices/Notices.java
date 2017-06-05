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
import android.support.v4.widget.TextViewCompat;
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

import com.cocodev.myapplication.R;
import com.cocodev.myapplication.adapter.CustomNoticeCursorAdapter;
import com.cocodev.myapplication.data.Contract;

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
        Log.e("his","onCreate");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("his","onCreateView");
        mView  = inflater.inflate(R.layout.fragment_notices, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(NOTICE_LOADER,null,this);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("his","onStart");

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
        String from[] = new String[]{
                Contract.NoticeEntry.COLUMN_DESCRIPTION
        };

        int to[] = new int[]{
                R.id.noticeText
        };

        ViewGroup viewGroup = (ViewGroup) mView.findViewById(R.id.notice_list_layout);
        mListView = new ListView(getContext());
        viewGroup.addView(mListView);


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



}
