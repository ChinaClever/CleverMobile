package com.clever.www.clevermobile.net.tcp.client;

import com.clever.www.clevermobile.common.array.ArrayUtil;
import com.clever.www.clevermobile.net.data.datadone.NetConstants;
import com.clever.www.clevermobile.net.data.recv.NetDataList;

/**
 * Created by lzy on 16-9-1.
 * tcp 接收线程
 */
public class TcpRecvThread implements Runnable{
    private static final int BUF_SIZE = 4*NetConstants.DATA_MSG_SIZE;
    private static byte[] searchArray = {NetConstants.DATA_MSG_ED, NetConstants.DATA_MSG_HDR, NetConstants.DATA_MSG_STX};

    private TcpClient mTcpClient =  TcpClient.get();
    private byte[] mRecvBuf = new byte[BUF_SIZE]; // 接收缓冲区
    private NetDataList mNetDataList = NetDataList.get();
    private static TcpRecvThread mRecvThread = null;
    private boolean isRun = true;

    /**
     * 获取唯一接收线程
     * @return
     */
    public static TcpRecvThread get() {
        if(mRecvThread == null)
            mRecvThread = new TcpRecvThread();
        return mRecvThread;
    }

    /**
     * 读取过来的数据进行转为小包, 防止丢包
     */
    private void readRecvBuf(String ip, byte[] buf, int len) {
        int offset = 0;

        while(offset < len) {
            int index =  ArrayUtil.searchArray(buf, searchArray, offset) + 1;
            if(index > 10) {
                byte[] array = ArrayUtil.copyOfRange(buf, offset, index);
                if((array[0] == NetConstants.DATA_MSG_HDR) && (array[array.length-1] == NetConstants.DATA_MSG_ED)) {
                    mNetDataList.addTcp(ip, array, array.length);
                    offset = index;
                } else {
                    break;
                }
            }  else {
                mNetDataList.addTcp(ip, buf, len);
                break;
            }
        }
    }

    /**
     * 接收数据 保存至链表中
     */
    private int recvData() {
        int ret = mTcpClient.recv(mRecvBuf);
        if(ret > 12) {
            String ip = mTcpClient.getServerIp();
            readRecvBuf(ip, mRecvBuf, ret);
        } else
            ret = -1;
        return ret;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if(isRun) {
                    if(recvData() <= 0)
                        Thread.sleep(1); // 每隔10毫抄接收一次数据
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setRun(boolean run) { isRun=run; }
}
