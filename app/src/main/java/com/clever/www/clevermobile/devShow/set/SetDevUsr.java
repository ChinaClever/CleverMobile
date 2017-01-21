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
import com.clever.www.clevermobile.pdu.data.packages.usr.PduDevUsr;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 16-11-30.
 */

public class SetDevUsr extends LinearLayout {
    private Context context =null;
    private PduDataPacket mDataPacket=null;
    private boolean isRun = false, isEdit = false;

    public SetDevUsr(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.set_dev_usr, this);
        this.context = context;
        initEditText();
        initButton();
    }

    public void setDataPacket(PduDataPacket data) {
        if(!isRun) {
            isRun = true;
            mDataPacket = data;
            isRun = false;
        }
    }

    private void initEditText() {
        EditText usrEdit = (EditText) findViewById(R.id.usrEdit);
        usrEdit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {  // 此处为得到焦点时的处理内容
                    isEdit = true;
                } else {  // 此处为失去焦点时的处理内容


                }
            }
        });

        EditText pwdEdit = (EditText) findViewById(R.id.pwdEdit);
        pwdEdit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
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
                    saveUsr();
            }
        });
    }

    private boolean setDev(String str) {
        SetDevCom setDevCom = SetDevCom.get();
        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = 6;
        pkt.fn[1] = 0x11;
        pkt.len = setDevCom.stringToByteList(str, pkt.data);

        return setDevCom.setDevData(pkt, false);
    }

    private void saveUsr() {
        EditText usrEdit = (EditText) findViewById(R.id.usrEdit);
        String usr = usrEdit.getText().toString();
        if(usr.isEmpty()) {
            Toast.makeText(context, R.string.set_usr_null, Toast.LENGTH_LONG).show();
            return;
        }

        EditText pwdEdit = (EditText) findViewById(R.id.pwdEdit);
        String pwd = usrEdit.getText().toString();
        if(pwd.isEmpty()) {
            Toast.makeText(context, R.string.set_pwd_null, Toast.LENGTH_LONG).show();
            return;
        }

        String str = usr + "; " + pwd;
        boolean ret = setDev(str);
        if(ret)
            Toast.makeText(context, R.string.set_save_ok, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, R.string.set_err_connect, Toast.LENGTH_LONG).show();

        isEdit = false;
    }

    private void setEditText() {
        List<String> list = new ArrayList<>();
        int ret = mDataPacket.usr.usrHash.getUsrList(list);
        if(ret > 0) {
            String name = list.get(0);
            PduDevUsr usr = mDataPacket.usr.usrHash.get(name);

            EditText usrEdit = (EditText) findViewById(R.id.usrEdit);
            usrEdit.setText(usr.usr.get());

            EditText pwdEdit = (EditText) findViewById(R.id.pwdEdit);
            pwdEdit.setText(usr.pwd.get());
        }
    }

    void updateData() {
        if(mDataPacket != null) {
            if(mDataPacket.offLine > 0) {
                if(!isEdit) {
                    setEditText();
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
