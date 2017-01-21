package com.clever.www.clevermobile.net.data.datadone;

/**
 * Created by lzy on 16-9-8.
 * 网络传输时，常量定义
 */
public interface NetConstants {
    public static final int TCP_PORT = 11283; // TCP连接端口

    public static final int DATA_MSG_SIZE = 1024; /*数据缓冲区大小*/
    public static final int DEV_CODE_SIZE = 4;		/*设备代号位数*/
    public static final int DEV_FN_SIZE = 2;       /*功能码长度*/
    public static final int DATA_MSG_CODE_SIZE = 9;  // 结构体大小 9个字节

    public static final int DATA_MSG_HDR = 0x7E;	/*信息头~*/
    public static final int DATA_MSG_STX = 0x5E;	/*标识字^*/
    public static final int DATA_MSG_ED = 0x23;	/*结束符*/

    public static final int DATA_MSG_SERVICE = 0x10;	/*服务端标志*/
    public static final int DATA_MSG_CLIENT = 0x03;	/*客户端标志*/
    public static final int DATA_DEV_VERSION = 1; /*设备通讯类型*/

    public static final int PDU_OFF_LINE_TIME = 5;
    public static final int TRA_TYPR_UDP=1; /*UDP通讯*/
    public static final int TRA_TYPR_TCP=2; /*TCP通讯*/

    /*错误码定义*/
    public static final int DATA_OK = 0;
    public static final int DATA_ERR_ABNORMAL = -1; /*数据不完整错误*/
    public static final int DATA_ERR_HEAD = -2; /*同步头错误*/
    public static final int DATA_ERR_END = -3; /*结束符错误*/
    public static final int DATA_ERR_XOR = -4;/*校验码错误*/
    public static final int DATA_ERR_LEN = -5; // 长度错误
    public static final int DATA_ERR_DATA_LEN = -6; // 数据域长度错误
}
