package com.clever.www.clevermobile.devShow.set;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;

/**
 * Author: lzy. Created on: 16-12-1.
 */

public class SetDevCmd extends LinearLayout {
    private Context context =null;
    private int mode = 1; // 重启设备 2 恢复出厂设置

    public SetDevCmd(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.set_dev_cmd, this);
        this.context = context;
        initRadio();
        initButton();
    }

    private void initRadio() {
        RadioGroup radgroup = (RadioGroup) findViewById(R.id.radioGroup);
        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.resRadio: mode = 1; break;
                    case R.id.factoryRadio: mode = 2; break;
                }
            }
        });
    }

    private boolean submitCmd() {
        SetDevCom setDevCom = SetDevCom.get();
        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = 20;
        switch (mode) {
            case 1: pkt.fn[1] = 3; break;
            case 2: pkt.fn[1] = 2; break;
        }
        pkt.len = 1;
        pkt.data.add((byte)1);
        return setDevCom.setDevData(pkt, false);
    }

    private void initButton() {
        Button btn = (Button)findViewById(R.id.submit);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                submitCmd();
            }
        });
    }

}
