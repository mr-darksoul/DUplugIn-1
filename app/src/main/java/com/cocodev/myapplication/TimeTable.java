package com.cocodev.myapplication;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTable extends Fragment   {

    RelativeLayout relativeLayout;
    private final int PERMISSION_WRITE_TO_STORAGE =1;
    private final int REQUEST_PERMISSION_SETTING =101;
    private  boolean sentToSettings = false;
    SharedPreferences permissionStatus;
    public TimeTable() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionStatus = getActivity().getSharedPreferences(
                "permissionStatus",
                getActivity().MODE_PRIVATE
        );
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


        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            )) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        getContext()
                );
                alertDialog.setTitle("Storage Permission Required");
                alertDialog.setMessage("This app needs storage permission to store your TimeTable offline. Do you want to try again? ");
                alertDialog.setPositiveButton(
                        "Grant",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(
                                        getActivity(),
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        PERMISSION_WRITE_TO_STORAGE
                                );
                            }
                        });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();


            } else if (permissionStatus.getBoolean(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    false
            )) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                        getContext()
                );
                alertDialog.setTitle("Storage Permission Denied");
                alertDialog.setMessage("This app needs storage permission.");
                alertDialog.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getContext(), "Go to Settings to Grant Storage", Toast.LENGTH_LONG).show();
                    }
                });


                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();

            } else {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_WRITE_TO_STORAGE
                );
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE,true);
            editor.commit();

        }else{
            //do your stuff

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_WRITE_TO_STORAGE){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                //call function to do stuff
            }
        }
    }
}
