package com.clever.www.clevermobile.net.data.recv;

import android.util.Log;

import com.clever.www.clevermobile.net.data.datadone.NetAnalyzeData;
import com.clever.www.clevermobile.net.data.packages.NetDataPacket;
import com.clever.www.clevermobile.pdu.data.hash.slave.PduHashDataSlave;


/**
 * Created by lzy on 16-9-8.
 * 网络数据包读取线程
 *  1、读取网络数据链表的数据包
 *  2、解析网络数据包
 *  3、保存至设备Hash表中
 *
 */
public class NetDataReadThread implements Runnable{
    private NetDataList mDataList = NetDataList.get();
    private NetAnalyzeData mAnalyzeData = new NetAnalyzeData();
    private PduHashDataSlave mDataSlave = new PduHashDataSlave();

    /**
     * 读取网络数据包，保存至设备Hash表中
     */
    private int readData(){
        int ret = -1;
        NetDataBase dataBase = mDataList.getData();
        if(dataBase != null) {
            NetDataPacket msg = new NetDataPacket();
            ret = mAnalyzeData.netDataAnalytic(dataBase.data, dataBase.data.length, msg);
            if(ret > 0) { // 保存至Hash表中
                switch (dataBase.type){
                    case TCP: // TCP 数据
                    case UDP: // UDP数据
                        mDataSlave.slave(dataBase.ip.toString(), msg); // 保存数据
                        break;
                }
            } else {
                Log.d("lzy", "recv err" + ret +" " + dataBase.ip.toString());
            }
        }
        return ret;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(readData() <= 0)
                    Thread.sleep(1); // 每隔1毫抄接收一次数据
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
