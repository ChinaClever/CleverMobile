package com.clever.www.clevermobile.pdu.data.packages.devinfo;

import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;

/**
 * Created by lzy on 16-9-2.
 */
public class PduDevType {

    public byte ms = -1; /*主从模式 1表示从机、0表示主机 默认为从机*/
    public PduStrBase name = new PduStrBase(); //设备名称
    public PduStrBase typeStr = new PduStrBase(); //设备型号
}
