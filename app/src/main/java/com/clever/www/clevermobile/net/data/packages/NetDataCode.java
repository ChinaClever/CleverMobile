package com.clever.www.clevermobile.net.data.packages;

import com.clever.www.clevermobile.net.data.datadone.NetConstants;

/**
 * Created by lzy on 16-9-8.
 * 代号段部分：
 * 1、设备代号
 * 2、通讯类型
 * 3、版本号
 * 4、传送标识
 */
public class NetDataCode {
    public byte[] devCode = new byte[NetConstants.DEV_CODE_SIZE]; /*设备代号*/
    public byte type; /*通讯类型*/
    public byte version; /*版本号*/

    public byte trans;	/*服务端发送标志：10H 客户端应答标志：03H */
    public short reserve; /*预留2字节*/
}
