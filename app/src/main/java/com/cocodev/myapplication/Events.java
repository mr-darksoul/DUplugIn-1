package com.cocodev.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.cocodev.myapplication.Utility.Article;
import com.cocodev.myapplication.Utility.Event;
import com.cocodev.myapplication.Utility.Notice;
import com.cocodev.myapplication.adapter.MyFragmentPageAdapter;
import com.cocodev.myapplication.notices.Notices;
import com.cocodev.myapplication.notices.Notices_ALL;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;


public class Events extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    public Events() {
        // Required empty public constructor

    }

    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        getActivity().setTitle("Events");
        //initViewPager(view);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addNotice);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event(
                        "College Auditorium",
                        "timestamp",
                        "descripton",
                        "www.facebook.com"
                );
                event.setUID(databaseReference.push().getKey());
                databaseReference.child("Categories").child("Events").child("Sports").child(event.getUID()).setValue(event.getUID());
                databaseReference.child("Articles").child(event.getUID()).setValue(event);
                Toast.makeText(getContext(),"FAB clicked",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void initViewPager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.events_viewPager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.events_tabLayout);

        List<Fragment> listFragmetns = new ArrayList<Fragment>();


        MyFragmentPageAdapter fragmentPageAdapter = new MyFragmentPageAdapter(getFragmentManager(),listFragmetns);
        viewPager.setAdapter(fragmentPageAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
