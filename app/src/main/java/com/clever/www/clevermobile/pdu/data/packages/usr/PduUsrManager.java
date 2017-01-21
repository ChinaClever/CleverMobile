package com.clever.www.clevermobile.pdu.data.packages.usr;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduUsrManager {
    public PduUsrHash usrHash = new PduUsrHash(); //用户表
    public PduUsrGroup group = new PduUsrGroup(); // 用户组
    public PduGroupRights rights = new PduGroupRights(); //输出位权限设置信息
}
