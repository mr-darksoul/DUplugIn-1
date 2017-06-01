package com.cocodev.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Sudarshan on 01-06-2017.
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragments;
    List<String> titles;
    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> listFragments,List<String> titles) {
        super(fm);
        this.listFragments=listFragments;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {

        return listFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {


        return titles.get(position);
    }
}
