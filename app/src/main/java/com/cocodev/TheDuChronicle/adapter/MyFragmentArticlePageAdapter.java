package com.cocodev.TheDuChronicle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cocodev.TheDuChronicle.articles.ArticleHolder;

import java.util.List;

/**
 * Created by Sudarshan on 01-06-2017.
 */

public class MyFragmentArticlePageAdapter extends FragmentStatePagerAdapter {

    List<ArticleHolder> listFragments;

    public MyFragmentArticlePageAdapter(FragmentManager fm, List<ArticleHolder> listFragments) {
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
        ArticleHolder articles = listFragments.get(position);

        return articles.getTypeString();
    }

    public void swapListFragments(List<ArticleHolder> listFragments){
        this.listFragments = listFragments;
    }


}
