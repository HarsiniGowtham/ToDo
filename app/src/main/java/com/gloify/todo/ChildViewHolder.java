package com.gloify.todo;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by apple on 11/7/16.
 */

public class ChildViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView time;

    public ChildViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.child);
        time = (TextView) itemView.findViewById(R.id.time);
    }
}
