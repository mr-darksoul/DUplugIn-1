package com.cocodev.myapplication;



import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.myapplication.adapter.CustomArticleHolderNoticeAdapter;
import com.cocodev.myapplication.adapter.MyFragmentPageAdapter;
import com.cocodev.myapplication.data.Contract;
import com.cocodev.myapplication.data.db.ContentProvider;
import com.cocodev.myapplication.notices.Notices;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    //DBAdapter dbAdapter;

    public Home() {
        // Required empty public constructor

    }

    ViewPager viewPager;
    TabHost tabhost;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_holder, container, false);

        initViewPager(view);
        getActivity().setTitle("App Name");


        return view;
    }
    private void initViewPager(View view) {

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
//        viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
//        List<Notices> listFragmetns = new ArrayList<Notices>();
//
//        Notices classNotices = new Notices();
//        classNotices.setType(Notices.TYPE_CLASS);
//        listFragmetns.add(classNotices);
//
//        Notices collegeNotices = new Notices();
//        collegeNotices.setType(Notices.TYPE_COLLEGE);
//        listFragmetns.add(collegeNotices);
//
//
//        Notices allNotices = new Notices();
//        allNotices.setType(Notices.TYPE_ALL);
//        listFragmetns.add(allNotices);
//
//        MyFragmentPageAdapter fragmentPageAdapter = new MyFragmentPageAdapter(getFragmentManager(),listFragmetns);
//
//        viewPager.setAdapter(fragmentPageAdapter);
//        viewPager.setOffscreenPageLimit(3);
//
//        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_home);
//        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.feed_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(),SettingsActivity.class);
            startActivity(intent);
        }
        return true;
    }


}
