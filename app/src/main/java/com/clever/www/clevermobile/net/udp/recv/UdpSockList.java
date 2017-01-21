package com.clever.www.clevermobile.net.udp.recv;

import com.clever.www.clevermobile.net.data.recv.NetDataReadThread;

/**
 * Author: lzy. Created on: 16-11-7.
 */

public class UdpSockList {
    private static final int SOCK_NUM = 20;
    private static final int PORT = 8080;
    private NetDataReadThread mNet = new NetDataReadThread();

    public void createSocket(final int num) {
        new Thread() {
            @Override
            public void run() {
                for(int i=0; i<num; ++i)
                    new UdpSocket().init(PORT + i);
            }
        }.start();
        new Thread(mNet).start();
    }

    public void createSocket() {createSocket(SOCK_NUM);}

}
