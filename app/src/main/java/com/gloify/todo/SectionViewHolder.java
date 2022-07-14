package com.gloify.todo;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by shanky on 11/12/2016.
 */

public class SectionViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    public SectionViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.sectionHeader);
    }
}
