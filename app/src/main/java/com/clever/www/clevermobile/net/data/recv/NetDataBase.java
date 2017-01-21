package com.clever.www.clevermobile.net.data.recv;

import com.clever.www.clevermobile.common.array.ArrayUtil;

/**
 * Created by lzy on 16-9-5.
 * 网络数据包类基本组成
 * 包组成：ip地址，端口号、通讯类型、数据内容
 */
public class NetDataBase {
    public StringBuilder ip = new StringBuilder(); // IP地址
    public int port = -1;  // 端口号
    public NetTransType type; // UDP、TCP二种类型
    public byte[] data = null; // 数据内容

    /**
     * 增加数据
     * @param array 数组
     * @param len 长度
     */
    public void addData(byte[] array, int len) {
        if(len > 0) {
            data = ArrayUtil.copyAarry(array, len);

//            data = new byte[len];
//            for(int i=0; i<len; ++i) {
//                data[i] = array[i];
//            }
        }
    }
}
