package com.clever.www.clevermobile.devShow.lineList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clever.www.clevermobile.R;

import java.util.List;

/**
 * Author: lzy. Created on: 16-11-2.
 */
public class LineListAdapter extends ArrayAdapter<LineListItem> {
    private int resourceId;
    public LineListAdapter(Context context, int resource, List<LineListItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

     private void setValue(TextView tv, double value, String str) {
        String string = "---";
        if(value >= 0)
            string = value + str;
        tv.setText(string);
    }

    private String getSymbol(int id) {
        String str="";
        switch (id) {
            case 1: str = "V"; break;
            case 2: str = "A"; break;
            case 3: str = "KW"; break;
            case 4: str = ""; break;
            case 5: str = "KWh"; break;
        }
        return str;
    }

    private void setColor(TextView tv, boolean alarm) {
        if(alarm)
            tv.setTextColor(Color.rgb(255, 0, 0));
        else
            tv.setTextColor(Color.rgb(0, 0, 0));
    }

    private void setViewValue(View view, LineListItem item) {
        String sym = getSymbol(item.getId());
        TextView tv = (TextView) view.findViewById(R.id.line1);
        setValue(tv, item.getValue(0), sym);
        boolean alarm = item.getAlarm(0) || item.getCrAlarm(0);
        setColor(tv, alarm);

        tv = (TextView) view.findViewById(R.id.line2);
        setValue(tv, item.getValue(1), sym);
        alarm = item.getAlarm(1) || item.getCrAlarm(1);
        setColor(tv, alarm);

        tv = (TextView) view.findViewById(R.id.line3);
        setValue(tv, item.getValue(2), sym);
        alarm = item.getAlarm(2) || item.getCrAlarm(2);
        setColor(tv, alarm);
    }


    private boolean setSwValue(TextView tv, boolean value) {
        boolean ret = true;
        if(value) {
            tv.setText(R.string.line_list_open);
            ret = false;
        }
        else
            tv.setText(R.string.line_list_close);
        return ret;
    }

    private void setSwView(View view, LineListItem item) {
        int line = 0;
        TextView tv = (TextView) view.findViewById(R.id.line1);
        boolean alarm = setSwValue(tv, item.getSw(line++));
        setColor(tv, alarm);

        tv = (TextView) view.findViewById(R.id.line2);
        alarm = setSwValue(tv, item.getSw(line++));
        setColor(tv, alarm);

        tv = (TextView) view.findViewById(R.id.line3);
        alarm = setSwValue(tv, item.getSw(line));
        setColor(tv, alarm);
    }

    private void setView(View view, LineListItem item) {
        TextView tv = (TextView) view.findViewById(R.id.name);
        tv.setText(item.getName());
        int id = item.getId();
        if(id == 0) {
            setSwView(view, item);
        } else {
            setViewValue(view, item);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LineListItem item = getItem(position);

        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        setView(view, item);

        return view;
    }


}
