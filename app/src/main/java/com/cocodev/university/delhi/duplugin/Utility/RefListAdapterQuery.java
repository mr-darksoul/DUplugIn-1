package com.cocodev.university.delhi.duplugin.Utility;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sudarshan on 20-06-2017.
 */

public abstract class RefListAdapterQuery<T> extends ArrayAdapter<T>{
    private Query[] db;
    private Query[] queries;
    public ChildEventListener childEventListener;
    private Class<T> modelclass;
    private int limit=2;
   // private String lastArticle="";
    private ListView mListView;
    private View mFooterView;
    private int resource;
    private long lastArticle;
    Time time = new Time();
    public RefListAdapterQuery(@NonNull Context context, Class<T> modelclass, @LayoutRes int resource, Query[] db) {
        super(context, resource);
        this.db=db;
        this.modelclass=modelclass;
        instantiateList();
        this.resource=resource;
    }


    private void instantiateList() {
        setChildEventListener();

        db[0].limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                lastArticle = Long.getLong(dataSnapshot.getKey(),0);
                time.normalize(false);
                time.set(lastArticle);
                time.monthDay -=15;
                lastArticle = time.toMillis(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        for (Query databaseReference:db
             ) {
            databaseReference.addChildEventListener(childEventListener);
        }

    }

    public void setChildEventListener() {
        childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                if(!lastArticle.equals(dataSnapshot.getKey())) {
//                    lastArticle = dataSnapshot.getKey();
                    T t = dataSnapshot.getValue(modelclass);
                    insert(t,0);
                //}
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                T t = dataSnapshot.getValue(modelclass);
                remove(t);
                notifyDataSetChanged();
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
        for (Query databaseReference:db
             ) {
            databaseReference.removeEventListener(childEventListener);
        }
        childEventListener=null;
    }

    @NonNull
    @Override
    public  View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }
        populateView(convertView,getItem(position),position);
        return convertView;
    }



    public void populateMoreList(final ListView listView,final View view){
        time.monthDay -= 15;
        long startAt = time.toMillis(true);
        for (Query databaseReference : db
                ) {
            databaseReference.orderByKey().startAt(Long.toString(startAt)).endAt(Long.toString(lastArticle-1)).addChildEventListener(childEventListener);
        }
        lastArticle=startAt;
    }

    protected abstract void populateView(View v, T model, int position);


}