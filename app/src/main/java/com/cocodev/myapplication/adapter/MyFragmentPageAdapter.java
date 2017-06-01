package com.cocodev.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cocodev.myapplication.notices.Notices;

import java.util.List;

/**
 * Created by Sudarshan on 01-06-2017.
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    List<Notices> listFragments;
    public MyFragmentPageAdapter(FragmentManager fm, List<Notices> listFragments) {
        super(fm);
        this.listFragments=listFragments;

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
        Notices notices = listFragments.get(position);

        return notices.getTypeString() + " Notices";
    }
}
