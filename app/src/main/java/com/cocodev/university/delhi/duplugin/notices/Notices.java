package com.cocodev.university.delhi.duplugin.notices;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cocodev.university.delhi.duplugin.NoticeDetails;
import com.cocodev.university.delhi.duplugin.R;
import com.cocodev.university.delhi.duplugin.SA;
import com.cocodev.university.delhi.duplugin.Utility.Notice;
import com.cocodev.university.delhi.duplugin.Utility.RefListAdapterQuery;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.cocodev.university.delhi.duplugin.Utility.Utility.getTimeAgo;


public class Notices extends Fragment  {

    public static final String key = "type";
    public static final String TYPE_HOME ="All";
    private String typeString;
    private View mView;
    private final int NOTICE_LOADER =1;
    private ListView mListView;


    public Notices() {}

    public static final Notices newInstance(String type){
        Notices n = new Notices();
        n.setTypeString(type);
        return n;
    }
    FirebaseDatabase firebaseDatabase;
    Query reference;
    RefListAdapterQuery mAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(savedInstanceState!=null){
                typeString = savedInstanceState.getString(key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView  = inflater.inflate(R.layout.fragment_notices, container, false);
        TextView textView = (TextView) mView.findViewById(R.id.notices_emptyView);
        textView.setText("There are currently no Notices under this Category.");
        mListView = (ListView) mView.findViewById(R.id.list_notices);
        mListView.setEmptyView(textView);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String college = sharedPreferences.getString(SA.KEY_COLLEGE,null);
        if(college!=null) {
            reference = firebaseDatabase.getReference().child("College Content")
                    .child(college)
                    .child("Notices")
                    .orderByChild("deadline")
                    .startAt(System.currentTimeMillis());

            mAdapter = new RefListAdapterQuery<Notice>(
                    getActivity(),
                    Notice.class,
                    R.layout.adapter_notice,
                    new Query[]{reference}
            ) {
                @Override
                protected void populateView(View v, Notice model, int position) {
                    final TextView uid = (TextView) v.findViewById(R.id.notice_uid);
                    final TextView title = (TextView) v.findViewById(R.id.notice_title);
                    final TextView time = (TextView) v.findViewById(R.id.notice_time);
                    final TextView deadline = (TextView) v.findViewById(R.id.notice_deadline);
                    deadline.setText(getTimeAgo(getContext(), model.getDeadline()));
                    time.setText(getTimeAgo(getContext(), model.getTime()));
                    title.setText(Html.fromHtml(model.getTitle()));
                    uid.setText(model.getUid());

                }
            };

            mListView.setAdapter(mAdapter);

        }



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView u = (TextView) view.findViewById(R.id.notice_uid);
                String uid = (String) u.getText();
                Intent intent = new Intent(getContext(), NoticeDetails.class);
                intent.putExtra(NoticeDetails.key,uid);
                startActivity(intent);
            }



        });
        return mView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    @Override
    public void onStart() {
        super.onStart();

    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getTypeString(){
        return typeString;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(key,typeString);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mAdapter!=null) {
            mAdapter.removeListener();
        }
    }
}
