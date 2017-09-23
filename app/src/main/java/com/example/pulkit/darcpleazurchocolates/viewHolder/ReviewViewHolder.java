package com.example.pulkit.darcpleazurchocolates.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pulkit.darcpleazurchocolates.R;

/**
 * Created by Pulkit on 9/23/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView bodyView;

    public ReviewViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.comment_author);
        bodyView = itemView.findViewById(R.id.comment_body);
    }
}
