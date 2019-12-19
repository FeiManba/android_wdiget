package com.mrzang.commonlibrary.widget.recyclerview;


import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Administrator on 2017/5/26.
 */

public interface StickyHeaderAdapter<T extends RecyclerView.ViewHolder> {
    long getHeaderId(int position);

    T onCreateHeaderViewHolder(ViewGroup parent);

    void onBindHeaderViewHolder(T viewholder, int position);
}
