package com.clever.www.clevermobile.devShow.line;

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
 * Author: lzy. Created on: 16-12-1.
 */

public class LineFragment extends Fragment{
    private LineCstDisplay mDis;
    private PduDataPacket mDataPacket=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_fragment, container, false);
//        mDis = (LineCstDisplay) view.findViewById(R.id.dis);
//        updatePacketThread();
        return view;
    }


    private void updateDataPacket() {
        mDataPacket = LoginStatus.getPacket();
        mDis.setDataPacket(mDataPacket);
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
