package com.clever.www.clevermobile.pdu.data.packages.usr;

import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduDevUsr {
    public PduStrBase usr = new PduStrBase(); // 用户
    public PduStrBase pwd = new PduStrBase(); // 密码

    public PduStrBase emil = new PduStrBase(); // 邮件
    public PduStrBase emil2 = new PduStrBase(); // 邮件
    public PduStrBase emil3 = new PduStrBase(); // 邮件

    public PduStrBase phone = new PduStrBase(); // 电话
    public PduStrBase group = new PduStrBase(); // 用户组
}
