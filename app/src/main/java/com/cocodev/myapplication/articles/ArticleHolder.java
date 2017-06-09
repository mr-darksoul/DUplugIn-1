package com.cocodev.myapplication.articles;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.cocodev.myapplication.R;
import com.cocodev.myapplication.adapter.CustomArticleHolderNoticeAdapter;
import com.cocodev.myapplication.data.Contract;
import com.cocodev.myapplication.data.FetchArticleTask;


public class ArticleHolder extends Fragment {

    private int TYPE =0;
    private final int TYPE_HOME = 0;
    private final int TYPE_COLLEGE = 1;
    private final int TYPE_SPORTS = 2;
    private final int TYPE_DANCE = 3;
    private final int TYPE_MUSIC = 4;
    private String SPORTS = "Sports";
    private String DANCE = "Dance";
    private String MUSIC = "Music";
    public ArticleHolder(){}

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
        if(c.moveToFirst())
            Toast.makeText(getContext(), Integer.toString(c.getCount()),Toast.LENGTH_SHORT).show();

        ListView listView = (ListView) view.findViewById(R.id.listView_articleHolder);
        CustomArticleHolderNoticeAdapter adapter = new CustomArticleHolderNoticeAdapter(
                getContext(),
                c,
                false
        );
        listView.setAdapter(adapter);

        return view;
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

}
