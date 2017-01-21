package com.clever.www.clevermobile.pdu.data.packages.devdata;

import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;

/**
 * Created by lzy on 16-9-2.
 * PDU数据对象类，主要包括
 *  电压、电流、功率、电能、功率因素、开关状态
 */
public class PduObjData {
    public PduDataUnit vol = new PduDataUnit(); // 电压
    public PduDataUnit cur = new PduDataUnit(); // 电流

    public PduDataBase pow = new PduDataBase(); // 功率
    public PduDataBase ele = new PduDataBase(); // 电能

    public PduDataBase pf = new PduDataBase(); // 功率因素
    public PduDataBase sw = new PduDataBase(); // 开关状态 0 表示未启用

    public PduDataBase carbon = new PduDataBase(); // 排碳量
    public PduDataBase rate = new PduDataBase(); // 电压频率
}
