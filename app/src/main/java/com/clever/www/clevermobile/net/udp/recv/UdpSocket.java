package com.clever.www.clevermobile.net.udp.recv;

import com.clever.www.clevermobile.net.data.recv.NetDataList;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Author: lzy. Created on: 16-11-7.
 */

public class UdpSocket {
    private static final int MAX_SIZE = 1024;
    private DatagramSocket socket = null;
    private DatagramPacket packet = null;
    private byte[] data = new byte[MAX_SIZE]; // 创建字节数组，指定接收的数据包的大小
    private NetDataList mNetDataList = NetDataList.get();

    public boolean init(int port) {
        boolean ret = true;
        try {
            socket = new DatagramSocket(port);  // 1.创建服务器端DatagramSocket，指定端口
            packet = new DatagramPacket(data, data.length);  // 2.创建数据报，用于接收客户端发送的数据

            recvThread();
        } catch (SocketException e) {
            e.printStackTrace();
            ret = false;
        }
        return ret;
    }

    private void readData() {
        String ip = packet.getAddress().toString();
        mNetDataList.addUdp(ip, data, packet.getLength());
    }

    private void recv() {
        try {
            socket.receive(packet);// 3.接收客户端发送的数据 此方法在接收到数据报之前会一直阻塞
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void recvThread() {
        new Thread() {
            @Override
            public void run() {
                while (true)
                    recv();
            }
        }.start();
    }

}
