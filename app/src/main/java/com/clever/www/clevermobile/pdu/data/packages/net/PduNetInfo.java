package com.clever.www.clevermobile.pdu.data.packages.net;

/**
 * Created by lzy on 16-9-2.
 */
public class PduNetInfo {
    public PduNetIP ip = new PduNetIP(); // IP地址
    public PduNetSNMP snmp = new PduNetSNMP();

    public PduNetSMTP smtp = new PduNetSMTP();
}
