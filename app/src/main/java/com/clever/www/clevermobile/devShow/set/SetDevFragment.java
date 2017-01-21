package com.clever.www.clevermobile.devShow.set;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.login.LoginStatus;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

/**
 * Author: lzy. Created on: 16-11-29.
 */

public class SetDevFragment extends Fragment{
    private PduDataPacket mDataPacket=null;
    private SetDevName mDevName = null;
    private SetDevUsr mDevUsr = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_dev_fragment, container, false);
        mDevName = (SetDevName) view.findViewById(R.id.devName);
        mDevUsr = (SetDevUsr) view.findViewById(R.id.usr);
        updatePacketThread();
        return view;
    }


    private void updateDataPacket() {
        mDataPacket = LoginStatus.getPacket();
        mDevName.setDataPacket(mDataPacket);
        mDevUsr.setDataPacket(mDataPacket);
    }

    private void updatePacketThread() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        updateDataPacket();
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
