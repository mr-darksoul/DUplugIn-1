package com.cocodev.myapplication.notices;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cocodev.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class allNotices extends Fragment {


    public allNotices() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_notices, container, false);
        String[] classNoticesArray = {
                "this is notice 0",
                "this is notice 1",
                "this is notice 2",
                "this is notice 3",
                "this is notice 4",

        };

        List<String> classNotices = new ArrayList<String>(
                Arrays.asList(classNoticesArray)
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.adapter_notice,R.id.noticeText,classNotices);
        ListView listViewClass = (ListView) view.findViewById(R.id.listview_allNotices);
        listViewClass.setAdapter(adapter);

        return view;
    }


}
