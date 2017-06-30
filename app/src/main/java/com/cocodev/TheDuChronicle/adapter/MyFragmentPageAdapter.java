package com.cocodev.TheDuChronicle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cocodev.TheDuChronicle.EH.EventsHolder;
import com.cocodev.TheDuChronicle.notices.Notices;
import com.cocodev.TheDuChronicle.notices.Notices_ALL;

import java.util.List;

/**
 * Created by Sudarshan on 01-06-2017.
 */

public class MyFragmentPageAdapter extends FragmentStatePagerAdapter {

    List<Fragment> listFragments;

    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> listFragments) {
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
        Fragment notices = listFragments.get(position);
        if(notices instanceof  Notices){
            return ((Notices) notices).getTypeString();
        }else if(notices instanceof Notices_ALL){
            return ((Notices_ALL) notices).getTypeString();
        }else if(notices instanceof EventsHolder){
            return ((EventsHolder) notices).getTypeString();
        }
        return null;
    }


}
