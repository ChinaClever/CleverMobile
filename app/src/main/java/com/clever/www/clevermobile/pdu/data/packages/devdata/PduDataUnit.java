package com.clever.www.clevermobile.pdu.data.packages.devdata;

import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;

/**
 * Created by lzy on 16-9-2.
 * PDU数据单元，主要包括
 *  1、最大值、最小值、报警值
 *  2、临界下值，临界上值，临界报警值
 */
public class PduDataUnit {
    public PduDataBase value = new PduDataBase(); // 当前值

    public PduDataBase min = new PduDataBase(); // 最小值
    public PduDataBase max = new PduDataBase(); // 最大值
    public PduDataBase alarm = new PduDataBase(); // 报警值
    public PduDataBase flag = new PduDataBase(); // 标志 0表示未纪录 1表示已纪录

    public PduDataBase crMin = new PduDataBase(); // 临界最小值
    public PduDataBase crMax = new PduDataBase(); // 临界最大值
    public PduDataBase crAlarm = new PduDataBase(); // 临界报警值
    public PduDataBase crFlag = new PduDataBase();
}
