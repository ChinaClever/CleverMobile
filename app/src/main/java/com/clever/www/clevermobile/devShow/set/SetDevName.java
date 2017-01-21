package com.clever.www.clevermobile.devShow.set;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

/**
 * Author: lzy. Created on: 16-11-30.
 */

public class SetDevName extends LinearLayout{
    private Context context =null;
    private PduDataPacket mDataPacket=null;
    private boolean isRun = false, isEdit = false;

    public SetDevName(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.set_dev_name, this);
        this.context = context;
        initEditText();
        initButton();
        new Timers().start(500);
    }

    public void setDataPacket(PduDataPacket data) {
        if(!isRun) {
            isRun = true;
            mDataPacket = data;
            isRun = false;
        }
    }

    private void initEditText() {
        EditText et = (EditText) findViewById(R.id.editText);
        et.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {  // 此处为得到焦点时的处理内容
                    isEdit = true;
                } else {  // 此处为失去焦点时的处理内容


                }
            }
        });
    }

    private void initButton() {
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDataPacket != null)
                saveDevName();
            }
        });
    }

    private boolean setDevtName(String name) {
        SetDevCom setDevCom = SetDevCom.get();
        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = 5;
        pkt.fn[1] = 0x11;
        pkt.len = setDevCom.stringToByteList(name, pkt.data);

        return setDevCom.setDevData(pkt, false);
    }

    private void saveDevName() {
        EditText et = (EditText) findViewById(R.id.editText);
        String name = et.getText().toString();
        if(!name.isEmpty()) {
            boolean ret = setDevtName(name);
            if(ret)
                Toast.makeText(context, R.string.set_save_ok, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context, R.string.set_err_connect, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, R.string.set_name_null, Toast.LENGTH_LONG).show();
        }
        isEdit = false;
    }

    private void setEditText(String str) {
        EditText et = (EditText) findViewById(R.id.editText);
        et.setText(str);
    }

    void updateData() {
        if(mDataPacket != null) {
            if(mDataPacket.offLine > 0) {
                if(!isEdit) {
                    String name = mDataPacket.info.type.name.get();
                    setEditText(name);
                }
            }
        }
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            if(!isRun) {
                isRun = true;
                updateData();
                isRun = false;
            }
        }
    }

}
