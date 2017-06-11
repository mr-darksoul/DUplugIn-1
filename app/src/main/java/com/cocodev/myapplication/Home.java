package com.cocodev.myapplication;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.cocodev.myapplication.adapter.MyFragmentArticlePageAdapter;
import com.cocodev.myapplication.articles.ArticleHolder;
import com.cocodev.myapplication.data.FetchArticleTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    MyFragmentArticlePageAdapter fragmentPageAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    //DBAdapter dbAdapter;
    private final String LAST_VIEWED_PAGE = "lastViewedPage";
    public Home() {
        // Required empty public constructor

    }





    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("App Name");

        initViewPager(view,savedInstanceState);


        return view;
    }
    private void initViewPager(View view, Bundle savedInstanceState) {

        viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
        List<ArticleHolder> listFragmetns = new ArrayList<ArticleHolder>();
        listFragmetns.add(new ArticleHolder(0));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(sharedPreferences!=null){

            Set<String> set = sharedPreferences.getStringSet(getString(R.string.homeFeed_key),null);
            if(set!=null){

                    Iterator<String> iterator = set.iterator();
                    while(iterator.hasNext()){
                        int id = Integer.parseInt(iterator.next());
                        listFragmetns.add(new ArticleHolder(id));
                    }

            }


        }



         fragmentPageAdapter = new MyFragmentArticlePageAdapter(getFragmentManager(),listFragmetns);

        viewPager.setAdapter(fragmentPageAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_home);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onResume() {
        super.onResume();
        List<ArticleHolder> listFragmetns = new ArrayList<ArticleHolder>();
        listFragmetns.add(new ArticleHolder(0));
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if(sharedPreferences!=null){

            Set<String> set = sharedPreferences.getStringSet(getString(R.string.homeFeed_key),null);
            if(set!=null){

                Iterator<String> iterator = set.iterator();
                while(iterator.hasNext()){
                    int id = Integer.parseInt(iterator.next());
                    listFragmetns.add(new ArticleHolder(id));
                }
            }
        }
        if(fragmentPageAdapter==null){
            fragmentPageAdapter=new MyFragmentArticlePageAdapter(getChildFragmentManager(),listFragmetns);
        }else {
            fragmentPageAdapter.swapListFragments(listFragmetns);
            fragmentPageAdapter.notifyDataSetChanged();
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LAST_VIEWED_PAGE,viewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null){
            int LastViewedpage = savedInstanceState.getInt(LAST_VIEWED_PAGE,0);
            if(viewPager.getAdapter().getCount()>LastViewedpage) {
                viewPager.setCurrentItem(LastViewedpage);
            }
        }
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
