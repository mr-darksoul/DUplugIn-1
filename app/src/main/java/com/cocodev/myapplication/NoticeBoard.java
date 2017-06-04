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
import android.widget.Toast;

import com.cocodev.myapplication.adapter.CustomViewPager;
import com.cocodev.myapplication.adapter.MyFragmentPageAdapter;
import com.cocodev.myapplication.data.FetchDataTask;
import com.cocodev.myapplication.notices.Notices;

import java.util.ArrayList;
import java.util.List;


public class NoticeBoard extends Fragment {


    public NoticeBoard() {
        // Required empty public constructor

    }

    CustomViewPager viewPager;
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


        return view;
    }



    private void initViewPager(View view) {
        viewPager = (CustomViewPager) view.findViewById(R.id.viewPager_notices);
        List<Notices> listFragmetns = new ArrayList<Notices>();



        Notices classNotices = new Notices();
        classNotices.setType(Notices.TYPE_CLASS);
        listFragmetns.add(classNotices);

        Notices collegeNotices = new Notices();
        collegeNotices.setType(Notices.TYPE_COLLEGE);
        listFragmetns.add(collegeNotices);


        Notices allNotices = new Notices();
        allNotices.setType(Notices.TYPE_ALL);
        listFragmetns.add(allNotices);




        MyFragmentPageAdapter fragmentPageAdapter = new MyFragmentPageAdapter(getFragmentManager(),listFragmetns);

        viewPager.setAdapter(fragmentPageAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_notice);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

}
