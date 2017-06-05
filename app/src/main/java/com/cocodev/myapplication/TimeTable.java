package com.cocodev.myapplication;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTable extends Fragment {

    RelativeLayout relativeLayout;
    public TimeTable() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_time_table, container, false);
        return relativeLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        String state = Environment.getExternalStorageState();




//        int permissionCheck = ContextCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
//            Log.i("tag","Permission is not granted");
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    112);
//
//        }
//
//        if(Environment.MEDIA_MOUNTED.equals(state)) {
//            Toast.makeText(getContext(), "external sd card present", Toast.LENGTH_SHORT).show();
//            File root = Environment.getExternalStorageDirectory();
//            File dir = new File(root.getAbsolutePath()+"/KMV");
//            if(!dir.exists()){
//                Toast.makeText(getContext(), "Folder is absent", Toast.LENGTH_SHORT).show();
//                if(!dir.canWrite()){
//                    Toast.makeText(getContext(), "can not Write", Toast.LENGTH_SHORT).show();
//
//                }
//                dir.mkdirs();
//            }else{
//                Toast.makeText(getContext(), "Folder is present", Toast.LENGTH_SHORT).show();
//            }
//
//        }else{
//            Toast.makeText(getContext(),"external sd card Absent",Toast.LENGTH_SHORT).show();
//        }


    }



}
