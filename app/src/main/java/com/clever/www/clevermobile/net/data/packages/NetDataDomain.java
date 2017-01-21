package com.clever.www.clevermobile.net.data.packages;

import com.clever.www.clevermobile.net.data.datadone.NetConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzy on 16-9-8.
 * 数据域部分
 * 1、地址码
 * 2、功能码
 * 3、数据：数据长度、数据
 */
public class NetDataDomain {
    public byte addr=0;	/*地址*/
    public byte[] fn = new byte[NetConstants.DEV_FN_SIZE];	/*功能码*/

    public int len=0; /*数据长度*/
    public List<Byte> data = new ArrayList<>(); /*数据*/
}
