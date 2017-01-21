package com.clever.www.clevermobile.pdu.dev;

import com.clever.www.clevermobile.pdu.data.hash.data.PduDevHash;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashData;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashIP;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashTable;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduDevSpied {
    private PduHashData mHashData = PduHashTable.getHash();
    public int devNum=0, devLine=0, devOff=0, devAlarm=0;
    private static PduDevSpied mDevSpid = null;

    public static PduDevSpied get() {
        if(mDevSpid == null)
            mDevSpid = new PduDevSpied();
        return mDevSpid;
    }

    private void checkDevState() {
        int num = 0, alarmNum=0, lineNum=0, offNum=0;
        List<Integer> list = new ArrayList<>();
        mHashData.list(list);

        for(int i=0; i<list.size(); ++i)
        {
            PduHashIP hashIP =  mHashData.getHash(list.get(i));
            List<String> listIp = new ArrayList<>();
            hashIP.list(listIp);

            for(int j=0; j<listIp.size(); ++j)
            {
                PduDevHash hash = hashIP.get(listIp.get(j));
                List<Integer> listDev = new ArrayList<>();
                hash.list(listDev);

                for(int k=0; k<listDev.size(); ++k)
                {
                    num++; // 设备数量
                    PduDataPacket packet = hash.get(listDev.get(k));
                    if(packet.offLine > 0)
                    {
                        if(--packet.offLine > 0)
                        {
                            lineNum++; // 在线设备数量加一
                            if(packet.state > 0) { // 设备不正常
                                alarmNum++; // 报警数量增加1

                            } else { // 设备工作正常

                            }

                        } else { // 设备离线

                        }
                    } else {
                        offNum++; // 离线数量

                    }
                }
            }
        }

        devNum = num;
        devLine = lineNum;
        devAlarm = alarmNum;
        devOff = offNum;
    }

    public void startThread() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    checkDevState();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
