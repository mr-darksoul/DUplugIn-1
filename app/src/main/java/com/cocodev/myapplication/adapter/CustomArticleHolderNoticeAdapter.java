package com.cocodev.myapplication.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sudarshan on 08-06-2017.
 */

public class CustomArticleHolderNoticeAdapter extends CursorAdapter {

    private final int COUNT_VIEW_TYPE = 2;
    private final int VIEW_TYPE_FIRST=1;
    private final int VIEW_TYPE_SECOND=2;

    public CustomArticleHolderNoticeAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CustomArticleHolderNoticeAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    @Override
    public int getViewTypeCount() {
        return COUNT_VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 1 ? VIEW_TYPE_FIRST : VIEW_TYPE_SECOND;
    }

}
