package com.clever.www.clevermobile.pdu.data.packages.net;

import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;

/**
 * Created by lzy on 16-9-2.
 */
public class PduNetSMTP {
    public PduStrBase usr = new PduStrBase(); // SMTP账号
    public PduStrBase pwd = new PduStrBase(); // 密码

    public PduStrBase server = new PduStrBase();// SMTP服务器:
    public int port = 25; // 端口

    public PduStrBase mode = new PduStrBase(); // 认证方式
    public PduStrBase test = new PduStrBase(); // SMTP设置测试 接收账号
}
