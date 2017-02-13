package com.clever.www.clevermobile.devShow.loop;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clever.www.clevermobile.R;

import java.util.List;

import static com.clever.www.clevermobile.R.id.pow;

/**
 * Author: lzy. Created on: 17-2-10.
 */

public class LoopAdapter extends RecyclerView.Adapter<LoopAdapter.ViewHolder>{
    private List<LoopItem> mLoopItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView loopTv, airSwTv, curTv, powTv;
        public ViewHolder(View itemView) {
            super(itemView);

            loopTv = (TextView) itemView.findViewById(R.id.name);
            airSwTv = (TextView) itemView.findViewById(R.id.air_sw);
            curTv = (TextView) itemView.findViewById(R.id.cur);
            powTv = (TextView) itemView.findViewById(pow);
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

    /**
     * 更新显示回路状态信息
     * @param holder
     * @param loopItem
     */
    private void setView(ViewHolder holder, LoopItem loopItem) {
        holder.loopTv.setText(loopItem.getName()); // 设置回路名称

        int swStr = R.string.loop_list_open;
        int color = Color.BLACK;
        if(loopItem.getAirSw() == 0) { // 空开断开
            swStr = R.string.loop_list_close;
            color = Color.RED;
        } else if(loopItem.getAirSw() == 1) {// 空开接通
            color = Color.GREEN;
        }
        holder.airSwTv.setText(swStr);
        holder.airSwTv.setTextColor(color);

        // 电流显示部分
        String str = "---";
        double cur = loopItem.getCur();
        if(cur>=0)
            str = cur + "A";
        holder.curTv.setText(str);

        // 电流报警颜色
        if(loopItem.getCrAlarm() == 1)
            color = Color.YELLOW;
        else
            color = Color.BLACK;
        if(loopItem.getAlarm() == 1)
            color = Color.RED;
        holder.curTv.setTextColor(color);

        double pow = loopItem.getPow();
        if(pow >= 0)
            str = pow + "Kwh";
        else
            str = "---";
        holder.powTv.setText(str);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LoopItem loopItem = mLoopItemList.get(position);
        setView(holder, loopItem);
    }

    @Override
    public int getItemCount() {
        return mLoopItemList.size();
    }
}
