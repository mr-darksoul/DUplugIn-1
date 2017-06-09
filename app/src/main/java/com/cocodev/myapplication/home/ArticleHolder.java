package com.cocodev.myapplication.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cocodev.myapplication.R;


public class ArticleHolder extends Fragment {


    private ListView mlistView;

    public ArticleHolder() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.article_list_view_first, container, false);
        mlistView = (ListView) view.findViewById(R.id.listView_articleHolder);

        return view;
    }

}
