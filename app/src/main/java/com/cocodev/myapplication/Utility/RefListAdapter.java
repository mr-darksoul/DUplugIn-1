package com.cocodev.myapplication.Utility;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sudarshan on 20-06-2017.
 */

public abstract class  RefListAdapter<T> extends ArrayAdapter<T>{
    private DatabaseReference[] db;
    private ChildEventListener childEventListener;
    private Class<T> modelclass;

    public RefListAdapter(@NonNull Context context,Class<T> modelclass, @LayoutRes int resource,DatabaseReference[] db) {
        super(context, resource);
        this.db=db;
        this.modelclass=modelclass;
        instantiateList();
    }


    private void instantiateList() {
        setChildEventListener();

        for (DatabaseReference databaseReference:db
             ) {
            databaseReference.addChildEventListener(childEventListener);
        }
    }

    private void setChildEventListener() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                T t = dataSnapshot.getValue(modelclass);
                add(t);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                T t = dataSnapshot.getValue(modelclass);
                int position = getPosition(t);
                remove(t);
                insert(t,position);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                T t = dataSnapshot.getValue(modelclass);
                remove(t);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    //do Nothing
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    //error in connection
            }
        };
    }





    public void removeListener(){
        for (DatabaseReference databaseReference:db
             ) {
            databaseReference.removeEventListener(childEventListener);
        }
        childEventListener=null;
    }

    @NonNull
    @Override
    public abstract View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent);

}