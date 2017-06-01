package com.cocodev.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.cocodev.myapplication.adapter.MyFragmentPageAdapter;
import com.cocodev.myapplication.data.FetchDataTask;
import com.cocodev.myapplication.notices.allNotices;
import com.cocodev.myapplication.notices.classNotices;
import com.cocodev.myapplication.notices.collegeNotices;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoticeBoard extends Fragment {


    public NoticeBoard() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    TabHost tabhost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_board, container, false);

        initViewPager(view);
       /// initTabHost(view);

        return view;
    }

    private void initTabHost(View view) {
        tabhost = (TabHost) view.findViewById(android.R.id.tabhost);
        tabhost.setup();
        String[] tabNames = {"Class Notices","College Notices","All Notices"};

    }

    private void initViewPager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_notices);
        List<Fragment> listfragmetns = new ArrayList<Fragment>();
        List<String> titles = new ArrayList<String>();
        listfragmetns.add(new classNotices());
        titles.add("Class Notices");
        listfragmetns.add(new collegeNotices());
        titles.add("College Notices");
        listfragmetns.add(new allNotices());
        titles.add("All Notices");

        MyFragmentPageAdapter fragmentPageAdapter = new MyFragmentPageAdapter(getFragmentManager(),listfragmetns,titles);

        viewPager.setAdapter(fragmentPageAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_notice);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

}
