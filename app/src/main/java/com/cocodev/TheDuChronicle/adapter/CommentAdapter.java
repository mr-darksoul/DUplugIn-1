package com.cocodev.TheDuChronicle.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.cocodev.TheDuChronicle.Utility.RefListAdapter;
import com.cocodev.TheDuChronicle.R;
import com.cocodev.TheDuChronicle.Utility.Comment;


import java.util.List;

/**
 * Created by manav on 7/3/17.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.review_single_list, parent, false);
        }
        TextView commentUserName = (TextView)convertView.findViewById(R.id.user_name);
        TextView userComment = (TextView)convertView.findViewById(R.id.user_review);
        Comment comment = getItem(position);
        commentUserName.setText(comment.getName());
        userComment.setText(comment.getReview());
        return  convertView;
    }


}
