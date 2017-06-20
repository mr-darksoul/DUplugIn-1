package com.cocodev.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cocodev.myapplication.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.Query;

/**
 * Created by Sudarshan on 08-06-2017.
 */

/**
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 */
public abstract class CustomArticleHolderAdapter<T> extends FirebaseListAdapter<T> {

    private final int COUNT_VIEW_TYPE = 2;
    private static final int VIEW_TYPE_FIRST=0;
    private static final int VIEW_TYPE_SECOND=1;

    public CustomArticleHolderAdapter(Activity activity, ObservableSnapshotArray<T> dataSnapshots, @LayoutRes int modelLayout) {
        super(activity, dataSnapshots, modelLayout);
    }

    public CustomArticleHolderAdapter(Activity activity, SnapshotParser<T> parser, @LayoutRes int modelLayout, Query query) {
        super(activity, parser, modelLayout, query);
    }

    public CustomArticleHolderAdapter(Activity activity, Class<T> modelClass, @LayoutRes int modelLayout, Query query) {
        super(activity, modelClass, modelLayout, query);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = newView(mActivity,position,viewGroup);
        }
        T model = getItem(position);

        populateView(view,model,position);

        return view;
    }

    @Override
    protected abstract void populateView(View v, T model, int position);


    public static class ViewHolder {

        public final TextView titleView;
        public final TextView timeView;
        public final TextView authorView;
        public final TextView UID;


        public ViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.article_title);
            timeView = (TextView) view.findViewById(R.id.article_time);
            authorView = (TextView) view.findViewById(R.id.article_author);
            UID = (TextView) view.findViewById(R.id.article_UID);
        }
    }


    public View newView(Context context, int position, ViewGroup parent) {
        int viewType = getItemViewType(position);
        int layoutID = -1;
        if(viewType==VIEW_TYPE_FIRST){
            layoutID = R.layout.article_list_view_first;
        }else{
            layoutID = R.layout.article_list_view_second;
        }

        View view = LayoutInflater.from(context).inflate(layoutID,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }


    @Override
    public int getViewTypeCount() {
        return COUNT_VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        return  position == 0 ? VIEW_TYPE_FIRST : VIEW_TYPE_SECOND;
    }

}
