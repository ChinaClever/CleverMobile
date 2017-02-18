package com.clever.www.clevermobile.devShow.loop;

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
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduObjData;

/**
 * Author: lzy. Created on: 17-2-15.
 */

public class LoopDialog extends LinearLayout{
    private PduDataPacket mDataPacket=null;
    private int mLoopId = 0;

    public LoopDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.loop_dialog, this);
        new Timers().start(500);
    }

    public void setData(PduDataPacket dataPacket, int loopId) {
        mDataPacket = dataPacket;
        mLoopId = loopId;

        updateView();
    }

    /**
     * 在TextView上显示数据
     * @param value  值
     * @param tv 控件
     * @param sign 单位
     */
    private void setTvItem(double value, TextView tv, String sign) {
        String str = "---";
        if(value >= 0)
            str = value + sign;
        tv.setText(str);
    }

    /**
     * 电流报警颜色
     * @param tv 控件
     * @param alarm 报警值
     * @param crAlarm 临界报警值
     */
    private void setCurColor(TextView tv, int alarm, int crAlarm) {
        int color = Color.BLACK;

        if(crAlarm == 1)
            color = Color.YELLOW;
        else
            color = Color.BLACK;
        if(alarm == 1)
            color = Color.RED;
        tv.setTextColor(color);
    }

    /**
     *  设置数据单元  当前值，阈值，临界值
     * @param dataUnit 数据单元
     * @param rate 倍玄
     */
    private void setDataUnit(PduDataUnit dataUnit, double rate) {
        int i = mLoopId;

        // 显示电流
        double value = dataUnit.value.get(i) / rate;
        TextView tv = (TextView) findViewById(R.id.cur);
        setTvItem(value, tv, "A");
        setCurColor(tv, dataUnit.alarm.get(i), dataUnit.crAlarm.get(i));

        // 最小值
        value = dataUnit.min.get(i) / rate;
        tv = (TextView) findViewById(R.id.min);
        setTvItem(value, tv, "A");

        // 最大值
        value = dataUnit.max.get(i) / rate;
        tv = (TextView) findViewById(R.id.max);
        setTvItem(value, tv, "A");

        // 临界下限
        value = dataUnit.crMin.get(i) / rate;
        tv = (TextView) findViewById(R.id.crMin);
        setTvItem(value, tv, "A");

        // 临界上阴
        value = dataUnit.crMax.get(i) / rate;
        tv = (TextView) findViewById(R.id.crMax);
        setTvItem(value, tv, "A");

    }

    /**
     * 空开状态显示
     * @param sw
     */
    private void setSw(int sw) {
        TextView airSwTv = (TextView) findViewById(R.id.sw);
        int swStr = R.string.loop_list_open;
        int color = Color.BLACK;
        if(sw == 0) { // 空开断开
            swStr = R.string.loop_list_close;
            color = Color.RED;
        } else if(sw == 1) {// 空开接通
            color = Color.GREEN;
        }
        airSwTv.setText(swStr);
        airSwTv.setTextColor(color);
    }


    /**
     * 显示数据对象中的数据
     * @param objData 数据对象结构体
     */
    private void setObjData(PduObjData objData) {
        int i = mLoopId;
        double powRate = RateEnum.POW.getValue();
        double curRate = RateEnum.CUR.getValue();
        double eleRate = RateEnum.ELE.getValue();

        setSw(objData.sw.get(i)); // 开关状态
        setDataUnit(objData.cur, curRate);
        // 功率
        double value = objData.pow.get(i) / powRate;
        TextView tv = (TextView) findViewById(R.id.pow);
        setTvItem(value, tv, "KW");

        // 电能
        value = objData.pow.get(i) / eleRate;
        tv = (TextView) findViewById(R.id.ele);
        setTvItem(value, tv, "Kwh");
    }

    /**
     * 实始化界面
     */
    private void initView() {

        setSw(-1); // 空天状态初始化

        // 电流
        double value = -1;
        TextView tv = (TextView) findViewById(R.id.cur);
        setTvItem(value, tv, "A");
        setCurColor(tv, 0, 0);

        // 功率
         tv = (TextView) findViewById(R.id.pow);
        setTvItem(value, tv, "KW");

        // 电能
        tv = (TextView) findViewById(R.id.ele);
        setTvItem(value, tv, "Kwh");

        // 最小值
        tv = (TextView) findViewById(R.id.min);
        setTvItem(value, tv, "A");

        // 最大值
        tv = (TextView) findViewById(R.id.max);
        setTvItem(value, tv, "A");

        // 临界下限
        tv = (TextView) findViewById(R.id.crMin);
        setTvItem(value, tv, "A");

        // 临界上阴
        tv = (TextView) findViewById(R.id.crMax);
        setTvItem(value, tv, "A");
    }

    /**
     * 更新显示
     */
    private void updateView() {
        if(mDataPacket != null) {
            if (mDataPacket.offLine > 0) {
                setObjData(mDataPacket.data.loop);
            } else {
                initView();
            }
        }
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() { updateView(); }
    }

}
