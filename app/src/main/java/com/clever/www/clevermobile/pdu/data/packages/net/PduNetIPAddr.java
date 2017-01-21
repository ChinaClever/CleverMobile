package com.clever.www.clevermobile.pdu.data.packages.net;

import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;

/**
 * Created by lzy on 16-9-2.
 */
public class PduNetIPAddr {
    public byte mode = -1; /* 网络模式 0手动设置 1 自动获取*/
    public PduStrBase ip = new PduStrBase(); // iP
    public PduStrBase gw = new PduStrBase(); // 网关
    public PduStrBase mask = new PduStrBase(); // 子网码
    public PduStrBase dns = new PduStrBase(); // dns
    public PduStrBase dns2 = new PduStrBase(); // 备用dns
}
