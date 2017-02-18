package com.clever.www.clevermobile.devShow.loop;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.devShow.set.SetDevCom;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduObjData;

import java.util.ArrayList;
import java.util.List;

import static com.clever.www.clevermobile.R.id.crMax;
import static com.clever.www.clevermobile.R.id.crMin;
import static com.clever.www.clevermobile.R.id.min;

/**
 * Author: lzy. Created on: 17-2-16.
 */

public class LoopSetDlg extends LinearLayout{
    private PduDataPacket mDataPacket=null;
    int mLoopId = 0;

    public LoopSetDlg(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.loop_set_dlg, this);
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
     */
    private void setTvItem(double value, TextView tv) {
        String str = "---";
        if(value >= 0)
            str = value +"";
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
        setTvItem(value, tv);
        setCurColor(tv, dataUnit.alarm.get(i), dataUnit.crAlarm.get(i));

        // 最小值
        value = dataUnit.min.get(i) / rate;
        tv = (TextView) findViewById(R.id.min);
        setTvItem(value, tv);

        // 最大值
        value = dataUnit.max.get(i) / rate;
        tv = (TextView) findViewById(R.id.max);
        setTvItem(value, tv);

        // 临界下限
        value = dataUnit.crMin.get(i) / rate;
        tv = (TextView) findViewById(R.id.crMin);
        setTvItem(value, tv);

        // 临界上阴
        value = dataUnit.crMax.get(i) / rate;
        tv = (TextView) findViewById(R.id.crMax);
        setTvItem(value, tv);
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
        double curRate = RateEnum.CUR.getValue();

        setSw(objData.sw.get(i)); // 开关状态
        setDataUnit(objData.cur, curRate);
    }

    /**
     * 更新显示
     */
    private void updateView() {
        if(mDataPacket != null) {
            if (mDataPacket.offLine > 0) {
                setObjData(mDataPacket.data.loop);
            }
        }
    }


    /**
     * 设置位数
     * @return 0 统一设置
     */
    private byte getBit() {
        byte id = (byte) (mLoopId +1);
        CheckBox box = (CheckBox) findViewById(R.id.unifiedBox);
        if(box.isChecked())
            id = 0;
        return id;
    }

    /**
     * 是否需要全局设置
     * @return true
     */
    private boolean getWholeSet() {
        CheckBox box = (CheckBox) findViewById(R.id.wholeBox);
        return box.isChecked();
    }

    private boolean setLoop(int min, int max, int crMin, int crMax) {
        SetDevCom setDevCom = SetDevCom.get();

        List<Integer> list = new ArrayList<>();
        list.add(min);
        list.add(max);
        list.add(crMin);
        list.add(crMax);

        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = 0x71;
        pkt.fn[1] = getBit();
        pkt.len = setDevCom.intToByteList(list, pkt.data);

        return setDevCom.setDevData(pkt, getWholeSet());
    }



    /**
     * 获取控件的值
     * @return -1 值为空
     */
    private int getEtView(EditText tv) {
        int data = -1;
        String str = tv.getText().toString();
        if (str != null && str.length() > 0) {

            str = str.replace("A","");
            str = str.replace("---","-1");

            double temp = Double.parseDouble(str);
            if(temp > 0)
                data = (int) (temp * RateEnum.CUR.getValue());
        }

        return data;
    }

    private int setValue(EditText tv, PduDataBase dataBase) {
        int data = getEtView(tv);
        if(data >= 0)
            dataBase.set(mLoopId, data);
        return data;
    }


    private String checkData(int min, int max, int crMin, int crMax) {
        String str = "";
        if(max > 16*RateEnum.CUR.getValue()) {
            str = getResources().getString(R.string.loop_ret_max);
        }

        if(min > max) {
            str = getResources().getString(R.string.loop_ret_min);
        }

        if(crMin < min) {
            str = getResources().getString(R.string.loop_ret_crMin);
        }

        if(crMax > max) {
            str = getResources().getString(R.string.loop_ret_crMax);
        }

        return str;
    }

    private String checkThreshold() {
        EditText tv = (EditText) findViewById(min);
        int min = getEtView(tv);

        tv = (EditText) findViewById(R.id.max);
        int max = getEtView(tv);

        tv = (EditText) findViewById(crMin);
        int crMin = getEtView(tv);

        tv = (EditText) findViewById(crMax);
        int crMax = getEtView(tv);

        return checkData(min, max, crMin, crMax);
    }

    /**
     * 阈值保存
     */
    private boolean saveThreshold() {
        EditText tv = (EditText) findViewById(min);
        PduDataUnit dataUnit =  mDataPacket.data.output.cur;
        int min = setValue(tv, dataUnit.min);

        tv = (EditText) findViewById(R.id.max);
        int max = setValue(tv, dataUnit.max);

        tv = (EditText) findViewById(crMin);
        int crMin = setValue(tv, dataUnit.crMin);

        tv = (EditText) findViewById(crMax);
        int crMax = setValue(tv, dataUnit.crMax);

        return setLoop(min, max, crMin, crMax);
    }

    public String saveData() {
        String str = "";
        if(mDataPacket != null) {
            str = checkThreshold();
            if (!str.isEmpty()) {
                saveThreshold();
            }
        }

        return str;
    }

}
