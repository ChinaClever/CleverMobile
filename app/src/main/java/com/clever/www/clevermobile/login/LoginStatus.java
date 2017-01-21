package com.clever.www.clevermobile.login;

import com.clever.www.clevermobile.pdu.data.hash.read.PduHashRead;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

/**
 * Author: lzy. Created on: 16-11-3.
 */
public class LoginStatus {
    public static boolean isLogin = false; // 默认为非连接
    public static String login_ip;
    public static int login_devNum = -1;
    public static String login_usr;
    public static String login_pwd;
    public static boolean isRemember = false;


    private static PduHashRead mReadHash = new PduHashRead();
    public static PduDataPacket getPacket() {
        PduDataPacket dataPacket = null;
        if(isLogin) {
            dataPacket = mReadHash.getPacket(login_ip, login_devNum);
        }
        return dataPacket;
    }

    public static boolean getLogin() {
        if(isLogin) {
            PduDataPacket dataPacket = getPacket();
            if(dataPacket != null) {
                if(dataPacket.offLine == 0)
                    isLogin = false;
            } else {
                isLogin = false;
            }
        }

        return isLogin;
    }

}
