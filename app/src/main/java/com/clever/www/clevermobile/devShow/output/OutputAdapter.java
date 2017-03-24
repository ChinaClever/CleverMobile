package com.clever.www.clevermobile.devShow.output;

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
 * Author: lzy. Created on: 16-10-8.
 * 自定义输出位适配器，这个适配器继承自ArrayAdapter,并将泛型指写为Output
 */
public class OutputAdapter extends ArrayAdapter<Output> {
    private int mResourceId;

    /**
     * 重写了父类的一组构造函数，用于将上下文、ListView了项布局的数据都传进来
     */
    public OutputAdapter(Context context, int resource, List<Output> objects) {
        super(context, resource, objects);
        mResourceId = resource;
    }

    protected void setView(View view, Output output) {
        String str = null;
//        TextView id = (TextView) view.findViewById(R.id.id); // 输出位编号
//        str = (output.getId()+1) +"";
//        id.setText(str);

        TextView name = (TextView) view.findViewById(R.id.name); // 输出位名称
        name.setText(output.getName());

        TextView swTv = (TextView) view.findViewById(R.id.sw); // 开关状态
        int sw = output.getSw();
        if(sw<0) {
            swTv.setText("---");
            swTv.setTextColor(Color.BLACK);
        } else if(sw == 0){
            swTv.setText(R.string.output_close);
            swTv.setTextColor(Color.RED);
        } else {
            swTv.setText(R.string.output_open);
            swTv.setTextColor(Color.GREEN);
        }

        TextView curTv = (TextView) view.findViewById(R.id.cur);
        double cur = output.getCur();
        if(cur >= 0)
            str = cur + "A";
         else
            str = "---";
        curTv.setText(str);
        boolean curAlarm = output.getCurAlarm();
        boolean crAlarm = output.getCrAlarm();
        if(crAlarm)
            curTv.setTextColor(Color.YELLOW);
        if(curAlarm)
            curTv.setTextColor(Color.RED);
        else
            curTv.setTextColor(Color.rgb(0, 0, 0));

        TextView powTv = (TextView) view.findViewById(R.id.pow);
        double pow = output.getPow();
        if(pow >= 0)
            str = pow + "KW";
        else
            str = "---";
        powTv.setText(str);

//        TextView crTv = (TextView) view.findViewById(R.id.cr);
//        if(crAlarm)
//            crTv.setText(R.string.output_cr_alarm);
//        else
//            crTv.setText(R.string.output_cr_ok);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //在每个子项被滚动到屏幕内的时候会被调用
        Output output = getItem(position); // 获取当前项的实例

        View view;
        if(convertView == null) { //如果为空，就用LayoutInflater加载布局
            view = LayoutInflater.from(getContext()).inflate(mResourceId, null); // 加载传入的布局
//            if(position % 2 == 0) view.setBackgroundColor(Color.rgb(242,242,242));
        } else { // 不为空则直接对convertView进行重用
            view = convertView;
        }
        setView(view, output);

        return view; // 返回布局
    }
}
