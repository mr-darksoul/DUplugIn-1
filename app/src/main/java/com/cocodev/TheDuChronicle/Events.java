package com.cocodev.TheDuChronicle;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocodev.TheDuChronicle.EH.EventsHolder;
import com.cocodev.TheDuChronicle.adapter.MyFragmentPageAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h6ah4i.android.tablayouthelper.TabLayoutHelper;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Events extends Fragment {
    private static boolean EVENTS_PREFERENCES_CHANGED = false;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    TabLayoutHelper tabLayoutHelper;
    public Events() {
        // Required empty public constructor

    }

    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isEventsPreferencesChanged())
            setEventsPreferencesChanged(false);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        getActivity().setTitle("Events");
        initViewPager(view);


        return view;
    }


    private void initViewPager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.events_viewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.events_tabLayout);

        List<Fragment> listFragmetns = new ArrayList<Fragment>();

        EventsHolder eventsHolder =EventsHolder.newInstance(EventsHolder.TYPE_HOME);
        listFragmetns.add(eventsHolder);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SA.fileName_EP, Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            Map<String ,?> map = sharedPreferences.getAll();
            for(Map.Entry<String,?>entry:map.entrySet()){
                if(entry.getValue().toString().equals("true")){
                    listFragmetns.add(EventsHolder.newInstance(entry.getKey()));
                }
            }
        }

        MyFragmentPageAdapter fragmentPageAdapter = new MyFragmentPageAdapter(getFragmentManager(),listFragmetns);
        viewPager.setAdapter(fragmentPageAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
         tabLayoutHelper = new TabLayoutHelper(tabLayout,viewPager);
        tabLayoutHelper.setAutoAdjustTabModeEnabled(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(isEventsPreferencesChanged()){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new Events()).commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    public static boolean isEventsPreferencesChanged() {
        return EVENTS_PREFERENCES_CHANGED;
    }

    public static void setEventsPreferencesChanged(boolean eventsPreferencesChanged) {
        EVENTS_PREFERENCES_CHANGED = eventsPreferencesChanged;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(tabLayoutHelper!=null) {
            tabLayoutHelper.release();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setTitle("Events");
    }

}
