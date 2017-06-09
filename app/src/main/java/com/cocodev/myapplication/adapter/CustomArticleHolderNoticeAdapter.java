package com.cocodev.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cocodev.myapplication.R;
import com.cocodev.myapplication.data.Contract;

/**
 * Created by Sudarshan on 08-06-2017.
 */

public class CustomArticleHolderNoticeAdapter extends CursorAdapter {

    private final int COUNT_VIEW_TYPE = 2;
    private static final int VIEW_TYPE_FIRST=0;
    private static final int VIEW_TYPE_SECOND=1;

    public CustomArticleHolderNoticeAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CustomArticleHolderNoticeAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public static class ViewHolder {

        public final TextView titleView;
        public final TextView timeView;
        public final TextView authorView;


        public ViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.article_title);
            timeView = (TextView) view.findViewById(R.id.article_time);
            authorView = (TextView) view.findViewById(R.id.article_author);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
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
    public void bindView(View view, Context context, Cursor cursor) {
        int viewType = cursor.getPosition();
        ViewHolder v = (ViewHolder) view.getTag();
        v.titleView.setText(cursor.getString(cursor.getColumnIndex(Contract.ArticleEntry.COLUMN_TITLE)));
        v.timeView.setText(cursor.getString(cursor.getColumnIndex(Contract.ArticleEntry.COLUMN_TIME)));
        if(v.authorView!=null){
            v.authorView.setText(cursor.getString(cursor.getColumnIndex(Contract.ArticleEntry.COLUMN_AUTHOR)));
        }

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
