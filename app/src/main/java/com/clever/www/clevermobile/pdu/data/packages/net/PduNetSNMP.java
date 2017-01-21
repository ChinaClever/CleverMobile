package com.clever.www.clevermobile.pdu.data.packages.net;

import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;

/**
 * Created by lzy on 16-9-2.
 */
public class PduNetSNMP {
    public boolean en; // V1、V2是否启用
    public PduStrBase get = new PduStrBase(); // GET共同体:
    public PduStrBase set = new PduStrBase(); //SET共同体

    public PduStrBase trap1 = new PduStrBase(); //Trap1地址
    public PduStrBase trap2 = new PduStrBase(); //Trap2地址

    public PduStrBase server = new PduStrBase();//SNMP服务器位置
    public PduStrBase node = new PduStrBase();//SNMP节点

    public boolean enV3; // SNMP v3
    public PduStrBase usr = new PduStrBase(); // 账号
    public PduStrBase pwd = new PduStrBase(); // 密码:
    public PduStrBase key = new PduStrBase(); // 私钥:
}
