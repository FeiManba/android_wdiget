package com.mrzang.commonlibrary.widget.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BasePopAdapter<D extends Object, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private Context mContext;
    private D[] mds;

    public D[] getMds() {
        return mds;
    }

    public BasePopAdapter(Context context, D[] mds) {
        mContext = context;
        this.mds = mds;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateChildViewHolder(parent, viewType);
    }

    protected abstract VH onCreateChildViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return mds != null ? mds.length : 0;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        D d = mds[position];
        onBindDataViewHolder(holder, position, d);
    }

    protected abstract void onBindDataViewHolder(VH holder, int position, D d);
}
