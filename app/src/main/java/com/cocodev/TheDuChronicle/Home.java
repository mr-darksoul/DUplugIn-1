package com.cocodev.TheDuChronicle;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cocodev.TheDuChronicle.Utility.Article;
import com.cocodev.TheDuChronicle.adapter.MyFragmentArticlePageAdapter;
import com.cocodev.TheDuChronicle.articles.ArticleHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private static boolean HOME_PREFERENCES_CHANGED = false;

    MyFragmentArticlePageAdapter fragmentPageAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    //DBAdapter dbAdapter;
    private final String LAST_VIEWED_PAGE = "lastViewedPage";
    public Home() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isHomePreferencesChanged())
            setHomePreferencesChanged(false);


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
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addArticles);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article event = new Article(
                        "Author",
                        "Here starts the description",
                        "20 min ago",
                        "tagline",
                        "null",
                        "This is the Title"
                );
                event.setUID(databaseReference.push().getKey());
                databaseReference.child("Categories").child("Articles").child("Sports").child(event.getUID()).setValue(event.getUID());
                databaseReference.child("Articles").child(event.getUID()).setValue(event);
                Toast.makeText(getContext(),"FAB clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void initViewPager(View view, Bundle savedInstanceState) {

        viewPager = (ViewPager) view.findViewById(R.id.viewPager_home);
        List<ArticleHolder> listFragmetns = new ArrayList<ArticleHolder>();
        listFragmetns.add(ArticleHolder.newInstance(ArticleHolder.TYPE_HOME));
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SA.fileName_HP, Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            Map<String ,?> map = sharedPreferences.getAll();
            for(Map.Entry<String,?>entry:map.entrySet()){
                if(entry.getValue().toString().equals("true")){
                    listFragmetns.add(ArticleHolder.newInstance(entry.getKey()));
                }
            }
        }



         fragmentPageAdapter = new MyFragmentArticlePageAdapter(getFragmentManager(),listFragmetns);

        viewPager.setAdapter(fragmentPageAdapter);
        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_home);
        tabLayout.setupWithViewPager(viewPager);
        TabLayoutHelper tabLayoutHelper = new TabLayoutHelper(tabLayout,viewPager);
        tabLayoutHelper.setAutoAdjustTabModeEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(HOME_PREFERENCES_CHANGED){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Home()).commit();
            HOME_PREFERENCES_CHANGED=false;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle(getString(R.string.app_name));
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
            Intent intent = new Intent(getActivity(),SA.class);
            startActivity(intent);
        }
        return true;
    }

    public static void setHomePreferencesChanged(boolean homePreferencesChanged) {
        HOME_PREFERENCES_CHANGED = homePreferencesChanged;
    }

    public boolean isHomePreferencesChanged() {
        return HOME_PREFERENCES_CHANGED;
    }
}
