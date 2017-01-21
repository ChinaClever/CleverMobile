package com.clever.www.clevermobile.net.udp.send;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Author: lzy. Created on: 16-11-7.
 */

public class UdpSend {

    /* 向服务器端发送数据 */
    private void sentData(String ip, int port, byte[] data, int len) {
        try {
        InetAddress address = InetAddress.getByName(ip);   // 1.定义服务器的地址、端口号、数据
        DatagramPacket packet = new DatagramPacket(data, len, address, port);  // 2.创建数据报，包含发送的数据信息
        DatagramSocket socket = new DatagramSocket();  // 3.创建DatagramSocket对象
            socket.send(packet);  // 4.向服务器端发送数据报
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 点对点发送 */
    public void sent(final String ip, final int port, final byte[] data, final int len) {
        new Thread() {
            @Override
            public void run() {
                sentData(ip, port, data, len);
            }
        }.start();
    }

    /* 广播发送*/
    public void dbSent(int port, byte[] data, int len) {
        String ip = "255.255.255.255";
        sent(ip, port, data, len);
    }

    public void dbSent(byte[] data, int len) {
        int port = 18750;
        dbSent( port, data, len);
    }

}
