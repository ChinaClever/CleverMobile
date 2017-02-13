package com.clever.www.clevermobile.pdu.data.packages.devdata;

/**
 * Created by lzy on 16-9-2.
 * PDU数据类，主要包括
 *  1、相数据
 *  2、输出位数据
 *  3、环境数据
 */
public class PduDevData {
    public PduObjData line = new PduObjData(); // 相数据 相若能耗散
    public PduObjData loop = new PduObjData(); // 回路数据
    public PduObjData output = new PduObjData(); //位数据

    public PduEnvData env = new PduEnvData(); // 环境数据
}
