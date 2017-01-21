package com.clever.www.clevermobile.net.udp.send;

/**
 * Author: lzy. Created on: 16-11-7.
 */

public class UdpHeartbeat {
    private static final int PORT = 8080;
    private static final String MSG = "Clever-Manager PDU PC Server OK!";
    UdpSend udp = new UdpSend();

    public void start() {
        sentThread();
    }

    private void sentThread() {
        new Thread() {
            @Override
            public void run() {
                while (true)
                    sent();
            }
        }.start();
    }

    private void sent() {
        try {
            Thread.sleep(1000);
            udp.dbSent(PORT, MSG.getBytes(), MSG.length());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
