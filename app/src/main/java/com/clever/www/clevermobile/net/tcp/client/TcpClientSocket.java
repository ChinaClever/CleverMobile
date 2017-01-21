package com.clever.www.clevermobile.net.tcp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by lzy on 16-8-31.
 *  TCP客户端的套接字操作
 */
public class TcpClientSocket {
    private static boolean isConnected=false;
    private static Socket mSocket = null;
    private static DataInputStream mInput = null;
    private static TcpClientSocket mTcpClientSocket = null; // 唯一对象

    /**
     * 获取唯一对象
     * @return
     */
    public static TcpClientSocket get() {
        if(mTcpClientSocket ==null) {
            mTcpClientSocket = new TcpClientSocket();
        }

        return mTcpClientSocket;
    }

    /**
     * 获取服务端的IP地址
     * @return
     */
    public String getServerIP(){
        String serIPAddr = "";
        if(mSocket != null) {
            if (isConnected) {
                serIPAddr = mSocket.getInetAddress().toString();
            }
        }

        return serIPAddr;
    }


    /**
     * 连接服务端
     * @param ip 服务端地址
     * @param port 目标端口
     */
    public void connectServer(String ip, int port) throws IOException {
        String serIPAddr  = getServerIP();
        if(!serIPAddr.contains(ip)) {// 目标地址是否一致
            if(mSocket != null) {
                closeSocket();    // 先关闭
            }

            mSocket = new Socket(ip, port);
            mInput = new DataInputStream(mSocket.getInputStream());
            isConnected = true;
        }
    }

    /**
     * 关闭套接字
     */
    public void closeSocket() {
        try {
            if(mSocket != null) {
                if (mSocket.isConnected()) {
                    mSocket.shutdownOutput();//关闭输出流
                    mSocket.close();
                    isConnected = false;
                    mInput.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送数据
     * @param data 数组
     * @param len 长度
     * @throws IOException
     */
    public void sentData(byte[] data, int len) throws IOException {
        if(mSocket != null) {
            DataOutputStream out = new DataOutputStream(mSocket.getOutputStream());
            out.write(data, 0, len);
        }
    }


    /**
     * 接收数据
     * @param data 接收缓冲区
     * @return 接收长度
     */
    public int recvData(byte[] data) {
        int ret = 0;
        try {
            if(mInput != null) {
                ret = mInput.read(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ret = 0;
        }

        return ret;
    }

    /**
     *  是否连接
     * @return
     */
    public boolean isConnect() {
        return isConnected;
    }
}
