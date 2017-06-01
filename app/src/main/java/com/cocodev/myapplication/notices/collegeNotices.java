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


public class collegeNotices extends Fragment {



    public collegeNotices() {
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
        View view = inflater.inflate(R.layout.fragment_college_notices, container, false);


        String[] collegeNoticesArray = {
                "this is college notice 0",
                "this is  college notice 1",
                "this is college  notice 2",
                "this is  college notice 3",
                "this is college  notice 4",
                "this is college notice 0",
                "this is  college notice 1",
                "this is college  notice 2",
                "this is  college notice 3",
                "this is college  notice 4",
                "this is college  notice 4",
                "this is college notice 0",
                "this is  college notice 1",
                "this is college  notice 2",
                "this is  college notice 3",
                "this is college  notice 4",
                "this is college  notice 5"
        };

        List<String> collegeNotices = new ArrayList<String>(
                Arrays.asList(collegeNoticesArray)
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.adapter_notice,R.id.noticeText,collegeNotices);
        ListView listViewCollege = (ListView) view.findViewById(R.id.listview_collegeNotices);
        listViewCollege.setAdapter(adapter);
        return view;
    }


}
