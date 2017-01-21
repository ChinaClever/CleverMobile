package com.clever.www.clevermobile.devShow.env;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.devShow.set.SetDevCom;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 16-10-29.
 */
public class EnvSetDlg extends LinearLayout{
    private PduDataUnit mDataUnit = null;
    private int mId = 0, mRate=1;
    private String mSymbol;

    public EnvSetDlg(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.env_set_dlg, this);
        new Timers().start(1000);
    }

    public void init(String title, String sym, PduDataUnit dataUnit, int id, int rate) {
        mDataUnit = dataUnit;
        mId = id;
        mSymbol = sym;
        mRate = rate;

        TextView tv = (TextView) findViewById(R.id.title);
        tv.setText(title);

        if(mDataUnit !=null) {
            updateValue();
            initThreshold();
        }
    }

    private void setView(TextView tv, int value) {
        String str = "---";
        if(value >= 0)
            str = value/mRate + mSymbol;
        tv.setText(str);
    }

    private void initThreshold() {
        TextView tv = (TextView) findViewById(R.id.min);
        int value = mDataUnit.min.get(mId);
        setView(tv, value);

        tv = (TextView) findViewById(R.id.max);
        value = mDataUnit.max.get(mId);
        setView(tv, value);
    }

    private void updateValue() {
        TextView tv = (TextView) findViewById(R.id.value);
        int value = mDataUnit.value.get(mId);
        setView(tv, value);

        value = mDataUnit.alarm.get(mId);
        if (value == 1) {
            tv.setTextColor(Color.rgb(255, 0, 0));
        } else {
            tv.setTextColor(Color.rgb(0, 0, 0));
        }
    }

    private void updateView() {
        if(mDataUnit != null)
            updateValue();
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
                updateView();
        }
    }

    private int getEtView(EditText tv) {
        int data = -1;
        String str = tv.getText().toString();
        if ((str != null) &&(str.length() > 0)){
            str = str.replace("°C","");
            str = str.replace("%","");
            data = Integer.parseInt(str);
        }

        return data;
    }

    private String checkData(int min, int max) {
        String str = "";
        if(max > 100) {
            str = getResources().getString(R.string.env_ret_max);
        }

        if(min > max) {
            str = getResources().getString(R.string.env_ret_min);
        }
        return str;
    }

    private String inputCheck() {
        EditText minTv = (EditText) findViewById(R.id.min);
        int min = getEtView(minTv);

        EditText maxTv = (EditText) findViewById(R.id.max);
        int max = getEtView(maxTv);

        return checkData(min, max);
    }

    private void slaveData() {
        EditText minTv = (EditText) findViewById(R.id.min);
        int min = getEtView(minTv) * mRate;
        if(min >= 0)
            mDataUnit.min.set(mId, min);

        EditText maxTv = (EditText) findViewById(R.id.max);
        int max = getEtView(maxTv) * mRate;
        if(max >= 0)
            mDataUnit.max.set(mId, max);

        setDev(min, max);
    }

    public String slave() {
        String str = inputCheck();
        if(str.isEmpty()) {
            slaveData();
        }
        return str;
    }

    /**
     * 获取工作模式
     * @return 3 温度  4 湿度
     */
    private byte getMode() {
        byte mode = 3; // 温度设置功能码
        if(mSymbol.contains("%"))
            mode = 4;
        return mode;
    }

    /**
     * 设置位数
     * @return 0 统一设置
     */
    private byte getBit() {
        byte id = (byte) (mId +1);
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


    private boolean setDev(int min, int max) {
        SetDevCom setDevCom = SetDevCom.get();

        List<Integer> list = new ArrayList<>();
        list.add(min);
        list.add(max);

        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = getMode();
        pkt.fn[1] = getBit();
        pkt.len = setDevCom.intToByteList(list, pkt.data);

        return setDevCom.setDevData(pkt, getWholeSet());
    }

}
