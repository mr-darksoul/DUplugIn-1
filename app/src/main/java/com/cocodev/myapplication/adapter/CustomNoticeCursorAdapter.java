package com.cocodev.myapplication.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cocodev.myapplication.NoticeBoard;
import com.cocodev.myapplication.R;
import com.cocodev.myapplication.data.Contract;

/**
 * Created by Sudarshan on 04-06-2017.
 */

public class CustomNoticeCursorAdapter extends CursorAdapter {
    int counter = 0;
    final Cursor mThisCursor;

    public CustomNoticeCursorAdapter(Context context, Cursor c, boolean autoRequery) {
       super(context, c, autoRequery);
        mThisCursor = c;
    }

    public CustomNoticeCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mThisCursor = c;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        View v;
        if (convertView == null) {
            v = newView(mContext, mCursor, parent);
        } else {
            v = convertView;
        }

        bindView(v, mContext, mCursor);
        return v;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_notice,parent,false);
        view.setTag(new ViewHolder(view));
        return view;
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        final CheckBox checkBox = viewHolder.checkBox;
        final TextView textView = viewHolder.textView;
        checkBox.setOnCheckedChangeListener(null);

        final int position = cursor.getPosition();

        String description = cursor.getString(
                mThisCursor.getColumnIndex(
                        Contract.NoticeEntry.COLUMN_DESCRIPTION
                )
        );
        int checked = cursor.getInt(
                mThisCursor.getColumnIndex(
                        Contract.NoticeEntry.COLUMN_CHECK
                )
        );
        textView.setText(description);

        if(checked == 1){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        final String ID = cursor.getString(
                cursor.getColumnIndex(
                        Contract.NoticeEntry._ID)
        );

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues contentValues = new ContentValues();
                if(isChecked){
                    contentValues.put(Contract.NoticeEntry.COLUMN_CHECK,"1");
                    context.getContentResolver().update(
                            Contract.NoticeEntry.CONTENT_URI,
                            contentValues,
                            Contract.NoticeEntry._ID + " = ? ",
                            new String[] {ID}
                    );
                }else{
                    contentValues.put(Contract.NoticeEntry.COLUMN_CHECK,"0");
                    context.getContentResolver().update(
                            Contract.NoticeEntry.CONTENT_URI,
                            contentValues,
                            Contract.NoticeEntry._ID + " = ? ",
                            new String[] {ID}
                    );
                }

            }
        });




    }


}
