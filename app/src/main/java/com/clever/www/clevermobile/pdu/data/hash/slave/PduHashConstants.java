package com.clever.www.clevermobile.pdu.data.hash.slave;

/**
 * Created by lzy on 16-9-18.
 * 常量定义
 */
public class PduHashConstants {
    public static final int PDU_CMD_STATUS = 1; //设备状态
    public static final int PDU_CMD_LINE = 2; // 相电参数
    public static final int PDU_CMD_OUTPUT = 3; // 输出位
    public static final int PDU_CMD_ENV = 4; // 环境数据

    public static final int PDU_CMD_TEMP = 1; //温度
    public static final int PDU_CMD_HUM = 2; // 湿度
    public static final int PDU_CMD_DOOR = 3; //门禁
    public static final int PDU_CMD_WATER = 4; //水浸
    public static final int PDU_CMD_SMOKE = 5; //烟雾

    public static final int PDU_CMD_DEVINFO = 5; // 设备信息
    public static final int PDU_CMD_DEVTYPE = 1; // 设备类型
    public static final int PDU_CMD_DEVADDR = 2; // 设备地址

    public static final int PDU_CMD_DEVUSR = 6; //设备信息 主功能码为6

    public static final int PDU_CMD_DEVNET = 7; //设备网络信息 主功能码为7
    public static final int PDU_CMD_NET_ADDR = 1; //IPV4信息
    public static final int PDU_CMD_NET_ADDRV6 = 2;  // IPV6信息
    public static final int PDU_CMD_NET_WIFI = 3;
    public static final int PDU_CMD_NET_HTTP = 4; //Http功能
    public static final int PDU_CMD_NET_SSH = 5;
    public static final int PDU_CMD_NET_FTP = 6;
    public static final int PDU_CMD_NET_MODBUS = 7;
    public static final int PDU_CMD_NET_SNMP = 8; // snmp相关
    public static final int PDU_CMD_NET_TELNET = 9; // Telent 相关
    public static final int PDU_CMD_NET_SMTP = 10;
    public static final int PDU_CMD_NET_NTP = 11;
    public static final int PDU_CMD_NET_RADIUS = 12;

    public static final int PDU_CMD_OUTPUT_NAME = 10; // 输出位名称

}
