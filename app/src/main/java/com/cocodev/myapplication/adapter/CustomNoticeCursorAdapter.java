package com.cocodev.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cocodev.myapplication.NoticeBoard;
import com.cocodev.myapplication.R;
import com.cocodev.myapplication.data.Contract;

/**
 * Created by Sudarshan on 04-06-2017.
 */

public class CustomNoticeCursorAdapter extends CursorAdapter {


    public CustomNoticeCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CustomNoticeCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private class ViewHolder{
        public CheckBox checkBox;
        public TextView textView;

        public ViewHolder(View view){
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            textView = (TextView) view.findViewById(R.id.noticeText);
        }



    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_notice,parent,false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        boolean checkbox;
        if(cursor.getInt(cursor.getColumnIndex(Contract.NoticeEntry.COLUMN_CHECK))==1){
            checkbox=true;
            viewHolder.checkBox.setChecked(true);
        }else{
            checkbox=false;
            viewHolder.checkBox.setChecked(false);
        }
        String description = cursor.getString(cursor.getColumnIndex(Contract.NoticeEntry.COLUMN_DESCRIPTION));
        viewHolder.textView.setText(description);
    }
}
