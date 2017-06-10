package com.cocodev.myapplication.articles;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.cocodev.myapplication.MyApplication;
import com.cocodev.myapplication.R;
import com.cocodev.myapplication.adapter.CustomArticleHolderNoticeAdapter;
import com.cocodev.myapplication.adapter.CustomNoticeCursorAdapter;
import com.cocodev.myapplication.data.Contract;
import com.cocodev.myapplication.data.FetchArticleTask;
import com.squareup.leakcanary.RefWatcher;


public class ArticleHolder extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private int TYPE =0;
    private final int TYPE_HOME = 0;
    private final int TYPE_COLLEGE = 1;
    private final int TYPE_SPORTS = 2;
    private final int TYPE_DANCE = 3;
    private final int TYPE_MUSIC = 4;
    private final int ARTICLE_LOADER = 1;
    private final String SPORTS = "Sports";
    private final String DANCE = "Dance";
    private final String MUSIC = "Music";

    public ArticleHolder(){}
    CustomArticleHolderNoticeAdapter mSimpleCursorAdapter;


    public ArticleHolder(int type) {
        // Required empty public constructor
        this.TYPE = type;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_holder, container, false);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshLayout_articleHolder);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchArticleTask fetchArticleTask = new FetchArticleTask(getContext(),swipeRefreshLayout);
                fetchArticleTask.execute();
            }
        });

        Cursor c = getContext().getContentResolver().query(
                Contract.ArticleEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );


        ListView listView = (ListView) view.findViewById(R.id.listView_articleHolder);
         mSimpleCursorAdapter = new CustomArticleHolderNoticeAdapter(
                getContext(),
                c,
                false
        );

        listView.setAdapter(mSimpleCursorAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ARTICLE_LOADER,null,this);
    }

    public String getTypeString(){
        switch(TYPE){
            case TYPE_SPORTS:
                return "Sports";
            case TYPE_DANCE:
                return "Dance";
            case TYPE_MUSIC:
                return "Music";
            case TYPE_HOME:
                return "Home";
            case TYPE_COLLEGE:
                return "College";
            default:
                return "Unknown Type";
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                Contract.ArticleEntry.CONTENT_URI,
                null,
                null,
                null,
                Contract.ArticleEntry.COLUMN_TIME);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSimpleCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mSimpleCursorAdapter.swapCursor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(ARTICLE_LOADER);
        RefWatcher refWatcher = MyApplication.getRefWatcher(getContext());
        refWatcher.watch(this);
    }
}
