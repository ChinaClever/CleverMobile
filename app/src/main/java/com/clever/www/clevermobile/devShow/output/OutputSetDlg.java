package com.clever.www.clevermobile.devShow.output;

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
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.devShow.set.SetDevCom;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;

import java.util.ArrayList;
import java.util.List;

import static com.clever.www.clevermobile.R.id.crMax;
import static com.clever.www.clevermobile.R.id.crMin;
import static com.clever.www.clevermobile.R.id.min;


/**
 * Author: lzy. Created on: 16-10-14.
 */
public class OutputSetDlg extends LinearLayout{
    private PduDataPacket mDataPacket=null;
    private int mLine = 0;

    public OutputSetDlg(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.output_set_dlg, this);

        new Timers().start(1000);
    }
    
    public void setData(PduDataPacket dataPacket, int line) {
        mDataPacket = dataPacket;
        mLine = line;

        if(mDataPacket != null) {
            setOutputName();
            initThreshold();
            updateCur();
        }
    }

    /**
     * 设置输出位的名称
     */
    private void setOutputName() {
        TextView tv = (TextView) findViewById(R.id.name);
        String name = mDataPacket.output.name.get(mLine);
        if(name == null)
            name = "Output" + (mLine+1);
        tv.setText(name);

        tv = (TextView) findViewById(R.id.num);
        String str = " " +  (mLine+1);
        tv.setText(str);
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
     * 初始化阈值
     */
    private void initThreshold() {
        EditText tv = (EditText) findViewById(min);
        double rate = RateEnum.CUR.getValue();
        double value = mDataPacket.data.output.cur.min.get(mLine) / rate;
        setCurTestView(value, tv);

        tv = (EditText) findViewById(R.id.max);
        value = mDataPacket.data.output.cur.max.get(mLine) / rate;
        setCurTestView(value, tv);

        tv = (EditText) findViewById(crMin);
        value = mDataPacket.data.output.cur.crMin.get(mLine) / rate;
        setCurTestView(value, tv);

        tv = (EditText) findViewById(crMax);
        value = mDataPacket.data.output.cur.crMax.get(mLine) / rate;
        setCurTestView(value, tv);
    }

    /**
     * 电流更新
     */
    private void updateCur() {
        TextView curTv = (TextView) findViewById(R.id.cur);
        double value = mDataPacket.data.output.cur.value.get(mLine) / RateEnum.CUR.getValue();
        setCurTestView(value, curTv);

        /**
         * 报警状态显示
         */
        TextView statusTv = (TextView) findViewById(R.id.status);
        int alarm = mDataPacket.data.output.cur.alarm.get(mLine);
        int crAlarm = mDataPacket.data.output.cur.crAlarm.get(mLine);
        if((alarm==1) || (crAlarm==1)) {
            if(alarm==1)
                statusTv.setText(R.string.output_alarm);
            else if(crAlarm == 1)
                statusTv.setText(R.string.output_cr_alarm);

            statusTv.setTextColor(Color.rgb(255, 0, 0));
            curTv.setTextColor(Color.rgb(255, 0, 0));
        } else {
            statusTv.setText(R.string.output_cr_ok);
            statusTv.setTextColor(Color.rgb(0, 0, 0));
            curTv.setTextColor(Color.rgb(0, 0, 0));
        }
    }

    void initView() {
        double value = -1;
        TextView tv = (TextView) findViewById(R.id.cur); // 电流恢复无效值
        setCurTestView(value, tv);

        tv = (TextView) findViewById(R.id.status);
        tv.setText(R.string.status_offLine);
        tv.setTextColor(Color.rgb(255, 0, 0));
    }

    private void updateView() {
        if(mDataPacket != null) {
            if (mDataPacket.offLine > 0) {
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

    /**
     * 设置位数
     * @return 0 统一设置
     */
    private byte getBit() {
        byte id = (byte) (mLine +1);
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

    private boolean setOutput(int min, int max, int crMin, int crMax) {
        SetDevCom setDevCom = SetDevCom.get();

        List<Integer> list = new ArrayList<>();
        list.add(min);
        list.add(max);
        list.add(crMin);
        list.add(crMax);

        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = 0;
        pkt.fn[1] = getBit();
        pkt.len = setDevCom.intToByteList(list, pkt.data);

        return setDevCom.setDevData(pkt, getWholeSet());
    }

    private boolean setOutputName(String name) {
        SetDevCom setDevCom = SetDevCom.get();
        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = 0;
        pkt.fn[1] = getBit();
        pkt.len = setDevCom.stringToByteList(name, pkt.data);

        return setDevCom.setDevData(pkt, getWholeSet());
    }


    /**
     * 保存输出位的名称
     * @return true 保存成功， false 保存失败
     */
    public boolean saveOutputName() {
        boolean ret = false;
        EditText ev = (EditText) findViewById(R.id.name);
        String name = ev.getText().toString();
        if(!name.isEmpty()) {
            if(mDataPacket != null) {
                mDataPacket.output.name.set(mLine, name);
                ret = setOutputName(name);
            }
        }
        return ret;
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
            dataBase.set(mLine, data);
        return data;
    }

    /**
     * 阈值保存
     */
    private boolean saveThreshold() {
        EditText tv = (EditText) findViewById(min);
        PduDataBase dataBase = mDataPacket.data.output.cur.min;
        int min = setValue(tv, dataBase);

        tv = (EditText) findViewById(R.id.max);
        dataBase = mDataPacket.data.output.cur.max;
        int max = setValue(tv, dataBase);

        tv = (EditText) findViewById(crMin);
        dataBase = mDataPacket.data.output.cur.crMin;
        int crMin = setValue(tv, dataBase);

        tv = (EditText) findViewById(crMax);
        dataBase = mDataPacket.data.output.cur.crMax;
        int crMax = setValue(tv, dataBase);

        return setOutput(min, max, crMin, crMax);
    }

    private String checkData(int min, int max, int crMin, int crMax) {
        String str = "";
        if(max > 16*RateEnum.CUR.getValue()) {
            str = getResources().getString(R.string.output_ret_max);
        }

        if(min > max) {
            str = getResources().getString(R.string.output_ret_min);
        }

        if(crMin < min) {
            str = getResources().getString(R.string.output_ret_crMin);
        }

        if(crMax > max) {
            str = getResources().getString(R.string.output_ret_crMax);
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
