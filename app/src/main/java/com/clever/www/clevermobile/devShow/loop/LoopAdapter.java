package com.clever.www.clevermobile.devShow.loop;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clever.www.clevermobile.R;

import java.util.List;

import static com.clever.www.clevermobile.R.id.pow;

/**
 * Author: lzy. Created on: 17-2-14.
 */

public class LoopAdapter extends ArrayAdapter<LoopItem>{
    private int resourceId;
    public LoopAdapter(Context context,  int textViewResourceId, List<LoopItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }


    private void setView(LoopItem loopItem, View itemView) {
        TextView loopTv = (TextView) itemView.findViewById(R.id.name);
        TextView airSwTv = (TextView) itemView.findViewById(R.id.air_sw);
        TextView curTv = (TextView) itemView.findViewById(R.id.cur);
        TextView powTv = (TextView) itemView.findViewById(pow);

        loopTv.setText(loopItem.getName()); // 设置回路名称

        int swStr = R.string.loop_list_open;
        int color = Color.BLACK;
        if(loopItem.getAirSw() == 0) { // 空开断开
            swStr = R.string.loop_list_close;
            color = Color.RED;
        } else if(loopItem.getAirSw() == 1) {// 空开接通
            color = Color.GREEN;
        }
        airSwTv.setText(swStr);
        airSwTv.setTextColor(color);

        // 电流显示部分
        String str = "---";
        double cur = loopItem.getCur();
        if(cur>=0)
            str = cur + "A";
        curTv.setText(str);

        // 电流报警颜色
        if(loopItem.getCrAlarm() == 1)
            color = Color.YELLOW;
        else
            color = Color.BLACK;
        if(loopItem.getAlarm() == 1)
            color = Color.RED;
        curTv.setTextColor(color);

        double pow = loopItem.getPow();
        if(pow >= 0)
            str = pow + "Kwh";
        else
            str = "---";
        powTv.setText(str);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LoopItem item = getItem(position);
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        setView(item, view);

        return view;
    }
}
