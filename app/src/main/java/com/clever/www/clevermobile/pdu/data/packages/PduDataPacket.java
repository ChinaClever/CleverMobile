package com.clever.www.clevermobile.pdu.data.packages;

import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDevData;
import com.clever.www.clevermobile.pdu.data.packages.devinfo.PduDevInfo;
import com.clever.www.clevermobile.pdu.data.packages.net.PduNetInfo;
import com.clever.www.clevermobile.pdu.data.packages.output.PduOutput;
import com.clever.www.clevermobile.pdu.data.packages.usr.PduUsrManager;

/**
 * Author: lzy. Created on: 16-9-29.
 * PDU设备数据包
 *      1、包含整个设备数据信息
 */
public class PduDataPacket {
    public int state = 0; // 工作状态 ==0 正常
    public int devNum = 0;  // 设备号
    public int devType = 0; //设备类型
    public int offLine = 0; // 离线标识
    public PduStrBase ip = new PduStrBase(); //设备IP

    public PduDevInfo info = new PduDevInfo(); // 设备型号、设备地址
    public PduDevData data = new PduDevData(); //设备数据
    public PduNetInfo net = new PduNetInfo();
    public PduOutput output = new PduOutput(); //输出位信息
    public PduUsrManager usr = new PduUsrManager(); //设备用户
}
