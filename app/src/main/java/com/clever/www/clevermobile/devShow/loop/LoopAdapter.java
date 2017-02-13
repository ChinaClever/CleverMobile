package com.clever.www.clevermobile.devShow.loop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clever.www.clevermobile.R;

import java.util.List;

/**
 * Author: lzy. Created on: 17-2-10.
 */

public class LoopAdapter extends RecyclerView.Adapter<LoopAdapter.ViewHolder>{
    private List<LoopItem> mLoopItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView loopTv;
        public ViewHolder(View itemView) {
            super(itemView);

            loopTv = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public LoopAdapter(List<LoopItem> loopItemList) {
        mLoopItemList = loopItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loop_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LoopItem loopItem = mLoopItemList.get(position);
        holder.loopTv.setText(loopItem.getName());

    }

    @Override
    public int getItemCount() {
        return mLoopItemList.size();
    }
}
