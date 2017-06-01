package com.cocodev.myapplication.notices;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cocodev.myapplication.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Notices extends Fragment {

    public static final String key = "type";
    public static final int TYPE_ALL = 0;
    public static final int TYPE_CLASS = 1;
    public static final int TYPE_COLLEGE = 2;
    public int type=-1;

    public Notices() {
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
        View view = inflater.inflate(R.layout.fragment_notices, container, false);

        TextView textView = (TextView) view.findViewById(R.id.title_notices);
        String title = getTypeString();
        textView.setText(title);

        String[] noticesArray = {
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

        List<String> notices = new ArrayList<String>(
                Arrays.asList(noticesArray)
        );

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.adapter_notice,R.id.noticeText,notices);
        ListView listViewNotices = (ListView) view.findViewById(R.id.listview_notices);
        listViewNotices.setAdapter(adapter);

        return view;
    }

    public void setType(int type){
        this.type=type;
    }

    public String getTypeString(){
        switch(type){
            case TYPE_ALL:
                return "All";
            case TYPE_CLASS:
                return "Class";
            case TYPE_COLLEGE:
                return "College";
        }
        return null;
    }

}
