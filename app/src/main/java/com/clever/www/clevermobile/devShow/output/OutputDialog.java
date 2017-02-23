package com.clever.www.clevermobile.devShow.output;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

/**
 * Author: lzy. Created on: 16-10-13.
 * 输出位点击弹出的对话框
 */
public class OutputDialog extends LinearLayout{
    private PduDataPacket mDataPacket=null;
    private Output mOutput = null;

    public OutputDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.output_dialog, this);
        new Timers().start(500);
    }

    public void setData(PduDataPacket dataPacket, Output output) {
        mDataPacket = dataPacket;
        mOutput = output;

        updateView();
    }

    /**
     * 更新输出位名称，工作状态，开关状态，输出电流等控件
     */
    private void updateInfoView() {
        String str;
        TextView idTv = (TextView) findViewById(R.id.id);
        str = (mOutput.getId()+1) +"";
        idTv.setText(str);

        TextView nameTv = (TextView) findViewById(R.id.name);
        nameTv.setText(mOutput.getName());

        TextView curTv = (TextView) findViewById(R.id.cur);
        TextView statusTv = (TextView) findViewById(R.id.status);
        boolean alarm = mOutput.getCurAlarm();
        boolean crAlarm = mOutput.getCrAlarm();
        if(alarm || crAlarm) {
            if(alarm) {
                statusTv.setText(R.string.output_alarm);
                curTv.setTextColor(Color.RED);
            } else if(crAlarm) {
                statusTv.setText(R.string.output_cr_alarm);
                curTv.setTextColor(Color.YELLOW);
            }
            statusTv.setTextColor(Color.rgb(255, 0, 0));

        } else {
            statusTv.setText(R.string.output_cr_ok);
            statusTv.setTextColor(Color.rgb(0, 0, 0));
            curTv.setTextColor(Color.rgb(0, 0, 0));
        }

        TextView swTv = (TextView) findViewById(R.id.sw);
        int sw = mOutput.getSw();
        if(sw > 0)
            swTv.setText(R.string.output_open);
        else
            swTv.setText(R.string.output_close);
    }

    /**
     * 更新输出数据
     */
    private void updateData() {
        String str ;
        TextView powTv = (TextView) findViewById(R.id.pow);
        double pow = mOutput.getPow();
        if(pow >= 0)
            str = pow + "KW";
        else
            str = "---";
        powTv.setText(str);

        TextView eleTv = (TextView) findViewById(R.id.ele);
        double ele =  mDataPacket.data.output.ele.get(mOutput.getId()) / RateEnum.ELE.getValue();
        if(ele >= 0)
            str = ele + "Kwh";
        else
            str = "---";
        eleTv.setText(str);

        TextView pfTv = (TextView) findViewById(R.id.pf);
        double pf = mDataPacket.data.output.pf.get(mOutput.getId()) / RateEnum.PF.getValue();
        if(pf >= 0)
            str = pf + "%";
        else
            str = "---";
        pfTv.setText(str);
    }

    private void setCurTestView(double cur, TextView view) {
        String str;
        if(cur >= 0)
            str = cur + "A";
        else
            str = "---";
        view.setText(str);
    }

    /**
     * 更新电流和阈值
     */
    private void updateCur() {
        TextView tv = (TextView) findViewById(R.id.cur);
        double rate = RateEnum.CUR.getValue();
        double value = mDataPacket.data.output.cur.value.get(mOutput.getId()) / rate;
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.min);
        value = mDataPacket.data.output.cur.min.get(mOutput.getId()) / rate;
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.max);
        value = mDataPacket.data.output.cur.max.get(mOutput.getId()) / rate;
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.crMin);
        value = mDataPacket.data.output.cur.crMin.get(mOutput.getId()) / rate;
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.crMax);
        value = mDataPacket.data.output.cur.crMax.get(mOutput.getId()) / rate;
        setCurTestView(value, tv);
    }

    private void initView() {
        double value = -1;
        TextView tv = (TextView) findViewById(R.id.cur); // 电流恢复无效值
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.pow);
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.pf);
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.status);
        tv.setText(R.string.status_offLine);
        tv.setTextColor(Color.rgb(255, 0, 0));

        mOutput.initData();
    }

    /**
     * 更新显示
     */
    private void updateView() {
        if(mDataPacket != null) {
            updateInfoView();
            if (mDataPacket.offLine > 0) {
                updateData();
                updateCur();
            } else
                initView();
        }
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            updateView();
        }
    }

}
