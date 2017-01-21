package com.clever.www.clevermobile.devShow.env;

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
 * Author: lzy. Created on: 16-10-27.
 */
public class EnvAdapter extends ArrayAdapter<EnvItem> {
    private int resourceId;

    public EnvAdapter(Context context, int resource, List<EnvItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    protected void  setValue(TextView tv, double value, String str) {
        String string = "---";
        if(value >= 0)
            string = value + str;
        tv.setText(string);
    }

    protected void setThView(View view, EnvItem env, String str) {
        double value = env.getValue();
        double min = env.getMin();
        double max = env.getMax();

        TextView tv = (TextView) view.findViewById(R.id.value);
        setValue(tv, value, str);
        boolean alarm = env.getAlarm();
        if(alarm)
            tv.setTextColor(Color.rgb(255, 0, 0));
        else
            tv.setTextColor(Color.rgb(0, 0, 0));

        tv = (TextView) view.findViewById(R.id.min);
        setValue(tv, min, str);

        tv = (TextView) view.findViewById(R.id.max);
        setValue(tv, max, str);
    }

    protected void setDoorView(View view, EnvItem env) {
        int value = env.getIValue();
        TextView tv = (TextView) view.findViewById(R.id.value);

        if(value == 1) {
            tv.setText(R.string.env_door_close);
            tv.setTextColor(Color.rgb(255, 0, 0));
        } else if(value == 0) {
            tv.setText(R.string.env_door_open);
            tv.setTextColor(Color.rgb(0, 0, 0));
        } else {
            tv.setText("---");
            tv.setTextColor(Color.rgb(0, 0, 0));
        }
    }

    protected void setOtherView(View view, EnvItem env) {
        int value = env.getIValue();
        TextView tv = (TextView) view.findViewById(R.id.value);

        if(value == 2) {
            tv.setText(R.string.env_alarm);
            tv.setTextColor(Color.rgb(255, 0, 0));
        } else if(value == 1) {
            tv.setText(R.string.env_ok);
            tv.setTextColor(Color.rgb(0, 0, 0));
        } else {
            tv.setText("---");
            tv.setTextColor(Color.rgb(0, 0, 0));
        }
    }

    protected void setView(View view, EnvItem env) {
        TextView nametv = (TextView) view.findViewById(R.id.name);
        nametv.setText(env.getSensorName());

        int id = env.getId();
        if(id < 4) {// 温度
            setThView(view, env,  "°C");
        } else if(id < 8){ // 湿度
            setThView(view, env, "%");
        } else if(id < 10) {
            setDoorView(view, env);
        } else {
            setOtherView(view, env);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EnvItem env = getItem(position);

        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        } else {
            view = convertView;
        }
        setView(view, env);

        return view;
    }
}
