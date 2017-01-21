package com.clever.www.clevermobile.pdu.data.hash.slave;

import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.net.PduNetIPAddr;
import com.clever.www.clevermobile.pdu.data.packages.net.PduNetInfo;
import com.clever.www.clevermobile.pdu.data.packages.net.PduNetSMTP;
import com.clever.www.clevermobile.pdu.data.packages.net.PduNetSNMP;
import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduHashDevNetSave {
    // IP地址结构体
    private static final int PDU_CMD_NET_IP = 1;
    private static final int PDU_CMD_NET_GW = 2;
    private static final int PDU_CMD_NET_MASK = 3;
    private static final int PDU_CMD_NET_DNS = 4;
    private static final int PDU_CMD_NET_DNS2 = 5;
    private static final int PDU_CMD_NET_MODE = 6;
    // SNMP 相关
    private static final int PDU_CMD_SNMP_EN = 1;
    private static final int PDU_CMD_SNMP_GET = 2;
    private static final int PDU_CMD_SNMP_SET = 3;
    private static final int PDU_CMD_SNMP_TRAP1 = 4;
    private static final int PDU_CMD_SNMP_TRAP2 = 5;
    private static final int PDU_CMD_SNMP_SERVER = 6;
    private static final int PDU_CMD_SNMP_NODE = 7;
    private static final int PDU_CMD_SNMP_ENV3 = 8;
    private static final int PDU_CMD_SNMP_USR = 9;
    private static final int PDU_CMD_SNMP_PWD = 10;
    private static final int PDU_CMD_SNMP_KEY = 11;

    private static final int PDU_CMD_SMTP_USR = 1;
    private static final int PDU_CMD_SMTP_PWD = 2;
    private static final int PDU_CMD_SMTP_SER = 3;
    private static final int PDU_CMD_SMTP_PORT = 4;
    private static final int PDU_CMD_SMTP_MODE = 5;
    private static final int PDU_CMD_SMTP_TEST = 6;


    private PduHashSlaveCom mCommon = new PduHashSlaveCom();

    /**
     * @brief 设备网络地址保存
     * @param ip
     * @param data
     */
     private void hashIPAddr(PduNetIPAddr ip, NetDataDomain data)  {
        PduStrBase str = null;
        int fc = data.fn[1] & 0x0f; // 获取高四位
        switch (fc) {
            case PDU_CMD_NET_IP:  str = ip.ip; break;
            case PDU_CMD_NET_GW:  str = ip.gw;  break;
            case PDU_CMD_NET_MASK: str = ip.mask;  break;
            case PDU_CMD_NET_DNS: str = ip.dns; break;
            case PDU_CMD_NET_DNS2:  str = ip.dns2;  break;
            case PDU_CMD_NET_MODE: //网络模式
                ip.mode = data.data.get(0);
                break;

            default: break;
        }
        if(str != null)  mCommon.devStrSave(str, data);
    }


    /**
     * @brief SNMP信息保存
     * @param snmp
     * @param data
     */
    private void hashSNMPSave(PduNetSNMP snmp, NetDataDomain data) {
        PduStrBase str = null;
        int fc = data.fn[1] & 0x0f; // 获取高四位
        switch(fc) {
            case PDU_CMD_SNMP_EN: // SNMP使能
                if(data.data.get(0)>0)
                    snmp.en = true;
                else
                    snmp.en = false;
                break;

            case PDU_CMD_SNMP_GET:  str = snmp.get;  break;
            case PDU_CMD_SNMP_SET:  str = snmp.set;  break;
            case PDU_CMD_SNMP_TRAP1: str = snmp.trap1;  break;
            case PDU_CMD_SNMP_TRAP2: str = snmp.trap2;  break;
            case PDU_CMD_SNMP_SERVER: str = snmp.server;  break;
            case PDU_CMD_SNMP_NODE: str = snmp.node;  break;
            case PDU_CMD_SNMP_ENV3:
                if(data.data.get(0) > 0)
                    snmp.enV3 = true;
                else
                    snmp.enV3 = false;
                break;

            case PDU_CMD_SNMP_USR: str = snmp.usr;  break;
            case PDU_CMD_SNMP_PWD: str = snmp.pwd;  break;
            case PDU_CMD_SNMP_KEY: str = snmp.key;  break;
            default:  break;
        }
        if(str != null) mCommon.devStrSave(str, data);
    }


    /**
     * @brief pdu_hashSmtp_save
     * @param smtp
     * @param data
     */
    private void hashSmtp(PduNetSMTP smtp, NetDataDomain data) {

        PduStrBase str = null;
        int fc = data.fn[1] & 0x0f; // 获取高四位
        switch(fc)
        {
            case PDU_CMD_SMTP_USR: str = smtp.usr;  break;
            case PDU_CMD_SMTP_PWD: str = smtp.pwd;  break;
            case PDU_CMD_SMTP_SER: str = smtp.server;  break;
            case PDU_CMD_SMTP_PORT:
                smtp.port = data.data.get(0) *256 + data.data.get(1);
                break;

            case PDU_CMD_SMTP_MODE: str = smtp.mode;  break;
            case PDU_CMD_SMTP_TEST: str = smtp.test;  break;
        }
        if(str != null) mCommon.devStrSave(str, data);
    }

    /**
     * @brief 设备用户信息保存
     * @param data
     */
    public void devNetSave(PduNetInfo net, NetDataDomain data)
    {
        int fc = data.fn[1] >> 4; // 获取高四位
        switch (fc)
        {
            case PduHashConstants.PDU_CMD_NET_ADDR: // IPV4 信息
                hashIPAddr(net.ip.ip, data);
                break;

            case PduHashConstants.PDU_CMD_NET_ADDRV6: // IPV6 信息
                hashIPAddr(net.ip.ip_v6, data);
                break;

            case PduHashConstants.PDU_CMD_NET_WIFI: // wifi
                break;

            case PduHashConstants.PDU_CMD_NET_HTTP: // Http
                break;

            case PduHashConstants.PDU_CMD_NET_SSH:
                break;

            case PduHashConstants.PDU_CMD_NET_FTP:
                break;

            case PduHashConstants.PDU_CMD_NET_MODBUS:
                break;

            case PduHashConstants.PDU_CMD_NET_SNMP: // SNMP
                hashSNMPSave(net.snmp, data);
                break;

            case PduHashConstants.PDU_CMD_NET_TELNET: // telnet
                break;

            case PduHashConstants.PDU_CMD_NET_SMTP:
                hashSmtp(net.smtp, data);
                break;

            case PduHashConstants.PDU_CMD_NET_NTP:
                break;

            case PduHashConstants.PDU_CMD_NET_RADIUS:
                break;

            default:
                break;
        }

    }
}
