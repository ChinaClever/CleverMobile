package com.clever.www.clevermobile.devShow.line;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.devShow.set.SetDevCom;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;

import java.util.ArrayList;
import java.util.List;

import static com.clever.www.clevermobile.R.id.min;

/**
 * Author: lzy. Created on: 16-12-12.
 */
public class LineSetDlg extends LinearLayout{
    private PduDataUnit mDataUnit = null;
    private int mId = 0, mRate=1;
    private String mSymbol;
    private Context context;

    public LineSetDlg(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_set_dlg, this);
        this.context = context;
        new Timers().start(1000);
    }

    public void init(String title, String sym, PduDataUnit dataUnit, int id, int rate) {
        mDataUnit = dataUnit;
        mId = id;
        mSymbol = sym;
        mRate = rate;

        TextView tv = (TextView) findViewById(R.id.title);
        tv.setText(title + "：");

        tv = (TextView) findViewById(R.id.sig);
        tv.setText(mSymbol);

        tv = (TextView) findViewById(R.id.minsig);
        tv.setText(mSymbol);

        tv = (TextView) findViewById(R.id.maxsig);
        tv.setText(mSymbol);

        if(mDataUnit !=null) {
            updateValue();
            initThreshold();
        }
    }

    private void setView(TextView tv, int value) {
        String str = "---";
        if(value >= 0)
            str = value/mRate +"";
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
        if (value == 1){
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

    private boolean setLine(int min, int max) {
        SetDevCom setDevCom = SetDevCom.get();

        List<Integer> list = new ArrayList<>();
        list.add(min);
        list.add(max);

        NetDataDomain pkt = new NetDataDomain();
        if(mSymbol.contains("V"))
            pkt.fn[0] = 1;
        else
            pkt.fn[0] = 2;
        pkt.fn[1] = getBit();
        pkt.len = setDevCom.intToByteList(list, pkt.data);

        return setDevCom.setDevData(pkt, getWholeSet());
    }


    /**
     * 获取控件的值
     * @return -1 值为空
     */
    private int getEtView(EditText tv) {
        int data = 0;
        String str = tv.getText().toString();
        if ((str != null) && (str.length() > 0)) {
            str = str.replace(mSymbol,"");
            str = str.replace("---","-1");

            double temp = Double.parseDouble(str);
            if(temp > 0)
                data = (int) (temp * mRate);
        }

        return data;
    }

    private int setValue(EditText tv, PduDataBase dataBase) {
        int data = getEtView(tv);
        if(data >= 0)
            dataBase.set(mId, data);
        return data;
    }

    /**
     * 阈值保存
     */
    private boolean saveThreshold() {
        EditText tv = (EditText) findViewById(min);
        PduDataBase dataBase = mDataUnit.min;
        int min = setValue(tv, dataBase);

        tv = (EditText) findViewById(R.id.max);
        dataBase = mDataUnit.max;
        int max = setValue(tv, dataBase);

        return setLine(min, max);
    }

    private String checkData(int min, int max) {
        String str = "";
        if(mSymbol.contains("A")) {
            if(max > 32*mRate) {
                str = getResources().getString(R.string.line_ret_curMax);
            }
        }

        if(min > max) {
            str = getResources().getString(R.string.output_ret_min);
        }

        return str;
    }

    private String checkThreshold() {
        EditText tv = (EditText) findViewById(min);
        int min = getEtView(tv);

        tv = (EditText) findViewById(R.id.max);
        int max = getEtView(tv);

        return checkData(min, max);
    }

    public String saveData() {
        String str = "";
        if(mDataUnit != null) {
            str = checkThreshold();
            if (str.isEmpty()) {
                saveThreshold();
//            } else {
//
            }
        }
        return str;
    }
}
