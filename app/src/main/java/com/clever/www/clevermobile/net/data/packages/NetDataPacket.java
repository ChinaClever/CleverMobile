package com.clever.www.clevermobile.net.data.packages;

/**
 * Created by lzy on 16-9-8.
 * 网络数据结构体定义，主要包括如下部分
 * 1、数据包同步头
 * 2、代号段部分
 * 3、数据域部分：数据长度、数据段
 * 4、结束部分：校验码和结束符
 */
public class NetDataPacket {
    public byte hdr;	/*信息头 0x7E*/
    public byte stx;	/*标识字0x5E*/
    public NetDataCode code = new NetDataCode(); /*代号段*/

    public int len; /*数据长度 */
    public NetDataDomain dataDomain = new NetDataDomain();	/*数据段*/
    public byte XOR; /*校验码*/
    public byte ED; /*结束符*/
}
