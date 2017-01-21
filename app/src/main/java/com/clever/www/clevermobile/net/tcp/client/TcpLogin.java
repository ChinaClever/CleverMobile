package com.clever.www.clevermobile.net.tcp.client;

/**
 * Author: lzy. Created on: 16-11-4.
 */
public class TcpLogin {
    private TcpClient mTcpClient = TcpClient.get();
    private TcpSingle mTcpSingle = TcpSingle.get();
    public boolean isLogin = false;

    public void connectSer(final String ip, final String usr, final String pwd, final int num) {
        new Thread() {
            @Override
            public void run() {
                login(ip, usr, pwd, num);
            }
        }.start();
    }

    public void disConnect() {
        new Thread() {
            @Override
            public void run() {
                if(mTcpClient.isConnect())
                    mTcpClient.close();
            }
        }.start();
    }

    private void login(String ip, String usr, String pwd, int num) {
        boolean ret =  connect(ip, usr, pwd, num);
        if(ret) {
            try {
                for(int i=0; i<10; ++i) {
                    Thread.sleep(1000);
                    ret = getAnswer();
                    if(ret) {
                        isLogin = true;
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void checkConnect() {
        mTcpSingle.stopThread(); // TCP 接收线程停止工作
        if(mTcpClient.isConnect()) {
            mTcpClient.close();
        }
    }

    private boolean connect(String ip, String usr, String pwd, int num) {

        checkConnect();
        boolean ret = mTcpClient.connect(ip);
        if(ret) {
            String str = usr + "; " +pwd + "; " + num;
            byte b[] = str.getBytes();
            ret = mTcpClient.sent(b, b.length);
        }
        return ret;
    }

    private boolean getAnswer() {
        boolean ret = mTcpClient.isConnect();
        if(ret) {
            byte[] data = new byte[512];
            int rtn = mTcpClient.recv(data);
            if(rtn > 0) {
                String str = new String(data);
                if(str.contains("OK")) {
                    ret = true;
                    mTcpSingle.startThread(); // TCp线程接收数据
                }
                else
                    ret = false;
            } else {
                ret = false;
            }
        }
        return ret;
    }

}
