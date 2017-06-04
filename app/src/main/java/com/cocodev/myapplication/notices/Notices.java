package com.cocodev.myapplication.notices;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

    private final int NOTICE_LOADER =1;

    CustomNoticeCursorAdapter mSimpleCursorAdapter;
    Cursor mCursor;
    public Notices() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCursor = getContext().getContentResolver().query(
                Contract.NoticeEntry.CONTENT_URI,
                new String[] {"*"}
                ,null
                ,null
                ,null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notices, container, false);


        mSimpleCursorAdapter  = new CustomNoticeCursorAdapter(getActivity(),mCursor,false);

        ListView listViewNotices = (ListView) view.findViewById(R.id.listview_notices);
        listViewNotices.setAdapter(mSimpleCursorAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(NOTICE_LOADER,null,this);
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


        return new CursorLoader(getActivity(),
                Contract.NoticeEntry.CONTENT_URI,
                null,
                null,
                null,
                Contract.NoticeEntry.COLUMN_CHECK);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSimpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSimpleCursorAdapter.swapCursor(null);
    }
}
