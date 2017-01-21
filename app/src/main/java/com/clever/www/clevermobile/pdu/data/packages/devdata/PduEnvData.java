package com.clever.www.clevermobile.pdu.data.packages.devdata;

import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;

/**
 * Created by lzy on 16-9-2.
 * PDU环境数据类，主要包括
 *  1、温湿度、门禁、水浸、烟雾等
 */
public class PduEnvData {
    public PduDataUnit tem = new PduDataUnit(); // 温度
    public PduDataUnit hum = new PduDataUnit(); // 湿度

    public PduDataBase door = new PduDataBase(); // 门禁
    public PduDataBase doorFlag = new PduDataBase();

    public PduDataBase water = new PduDataBase(); // 水浸
    public PduDataBase waterFlag = new PduDataBase();

    public PduDataBase smoke = new PduDataBase(); // 烟雾
    public PduDataBase smokeFlag = new PduDataBase();
}
