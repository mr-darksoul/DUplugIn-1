package com.cocodev.duplugin.notices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cocodev.duplugin.NoticeDetails;
import com.cocodev.duplugin.R;
import com.cocodev.duplugin.Utility.Notice;
import com.cocodev.duplugin.Utility.RefListAdapterQuery;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.cocodev.duplugin.Utility.Utility.getTimeAgo;


public class Notices_ALL extends Fragment  {

    public static final String key = "type";
    private String typeString ;
    private View mView;
    private final int NOTICE_LOADER =1;
    private ListView mListView;



    public Notices_ALL() {
        setTypeString("University");
    }

    FirebaseDatabase firebaseDatabase;
    Query reference;
    RefListAdapterQuery mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference()
                .child("Notices")
                .orderByChild("deadline")
                .startAt(System.currentTimeMillis());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView  = inflater.inflate(R.layout.fragment_notices, container, false);
        mListView = (ListView) mView.findViewById(R.id.list_notices);
        TextView textView = (TextView) mView.findViewById(R.id.notices_emptyView);
        textView.setText("There are currently no Notices under this Category.");
        mListView.setEmptyView(textView);
        mAdapter = new RefListAdapterQuery<Notice>(
                getActivity(),
                Notice.class,
                R.layout.adapter_notice,
                new Query[]{reference}
        ) {

            @Override
            protected void populateView(View v, Notice model, int position) {
                final TextView title = (TextView) v.findViewById(R.id.notice_title);
                final TextView time = (TextView) v.findViewById(R.id.notice_time);
                final TextView deadline = (TextView) v.findViewById(R.id.notice_deadline);
                final TextView uid = (TextView) v.findViewById(R.id.notice_uid);
                if(model==null)
                    return;
                uid.setText(model.getUid());
                title.setText(Html.fromHtml(model.getTitle()));
                time.setText(getTimeAgo(getContext(),model.getTime()));
                deadline.setText(getTimeAgo(getContext(),model.getDeadline()));
            }


        };

        mListView.setAdapter(mAdapter);
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
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.removeListener();
    }
}
