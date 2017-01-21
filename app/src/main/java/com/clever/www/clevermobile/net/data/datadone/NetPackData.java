package com.clever.www.clevermobile.net.data.datadone;

import com.clever.www.clevermobile.net.data.packages.NetDataCode;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.net.data.packages.NetDataPacket;

/**
 * Created by lzy on 16-9-12.
 * 把数据打包成网络数据包
 * 公共接口： int getDataPackets();
 */
public class NetPackData {

    /**
     * 数据加入包头、结束符
     * @param pkt
     */
    private void setPacketHead(NetDataPacket pkt) {
        pkt.hdr = NetConstants.DATA_MSG_HDR;	/*信息头*/
        pkt.stx = NetConstants.DATA_MSG_STX; /*标识字*/

        pkt.ED = NetConstants.DATA_MSG_ED; /*结束符*/
    }

    /**
     * 填入代号段数据
     * @return
     */
    private int setPacketCode(NetDataCode msg, byte[] buf, int ptr) {
    /*填入代号段*/
        for(int i=0; i<NetConstants.DEV_CODE_SIZE; ++i)
            buf[ptr++] = msg.devCode[i];	/*设备号*/

        buf[ptr++] = msg.type;	/*通讯类型*/
        buf[ptr++] = msg.version; /*版本号*/
        buf[ptr++] = msg.trans; /*传输方向*/

        msg.reserve = 0;
        buf[ptr++] = 0;
        buf[ptr++] = 0;

        return NetConstants.DATA_MSG_CODE_SIZE; /*代码段字节数为9*/
    }

    /**
     * 获取检验码
     */
    private byte getXOR(byte[] buf, int ptr, int len){
        byte XOR=0;
        for(int i=0; i<len; ++i)
            XOR += buf[ptr++];
        return XOR;
    }

    /**
     * 设备数据打包
     * @return
     */
    private byte setDataDomain(NetDataDomain data, byte[] buf, int ptr) {
        int startPtr = ptr;
        buf[ptr++] = data.addr; // 设备地址
        for(int i=0; i<NetConstants.DEV_FN_SIZE; ++i) /*功能码*/
            buf[ptr++] = data.fn[i];
        buf[ptr++] = (byte) ((data.len) >> 8); /*长度高8位*/
        buf[ptr++] = (byte) ((0xff)&(data.len)); /*低8位*/

        if(data.len < NetConstants.DATA_MSG_SIZE) {
            for (int i = 0; i < data.len; ++i)
                buf[ptr++] = data.data.get(i);
        }

        return getXOR(buf,startPtr, data.len);
    }

    /**
     * 填入数据域
     * @return
     */
    private int setPacketDomain(NetDataPacket msg, byte[] buf, int ptr)  {
    /*填入数据长度*/
        buf[ptr++] = (byte) ((msg.len) >> 8); /*长度高8位*/
        buf[ptr++] = (byte) ((0xff)&(msg.len)); /*低8位*/

        msg.XOR = setDataDomain(msg.dataDomain, buf,ptr);
        buf[ptr+msg.len] = msg.XOR;/*生成校验码*/

        return 2+ msg.len + 1;
    }

    /**
     * 数据打包
     * @return 数据包长度
     */
    private int setDataMsgPacket(NetDataPacket pkt, byte[] buf){
        int ptr = 0;
        setPacketHead(pkt); /*填写包头、包尾*/
        buf[ptr++] = pkt.hdr;  /*信息头*/
        buf[ptr++] = pkt.stx; /*标识字*/

        ptr += setPacketCode(pkt.code,buf, ptr); /*填入代号段数据*/
        ptr += setPacketDomain(pkt, buf,ptr); /*填入数据*/
        buf[ptr] = pkt.ED; /*结束符*/

        return  2 + NetConstants.DATA_MSG_CODE_SIZE + 2 + pkt.len + 1 + 1;
    }

    /**
     * 整形数据转化为字符符数据
     * @return
     */
    private int int_to_uchar(int form, byte[] to) {
        int offset=0;
        to[offset++] = (byte)(form >> 24); // 高位
        to[offset++] = (byte)((form >> 16) & 0xFF);
        to[offset++] = (byte)((form >> 8) & 0xFF);
        to[offset++] = (byte)(form & 0xFF); // 低8位

        return offset;
    }

    /**
     * 获取设备代号段
     * @return 设备代号段
     */
    private NetDataCode getDevCode(int num, byte type) {
        NetDataCode code = new NetDataCode();
        int_to_uchar(num, code.devCode);
        code.type = type;
        code.version = NetConstants.DATA_DEV_VERSION;
        code.trans = NetConstants.DATA_MSG_SERVICE; // 服务端标志
        code.reserve = 0; // 预留位

        return code;
    }

    // 获取网络数据包
    private NetDataPacket getDataPacket(NetDataCode code, int len, NetDataDomain data) {
        NetDataPacket msg =  new NetDataPacket();
        msg.code = code; /*代号段*/
        msg.len = len; /*数据长度 */
        msg.dataDomain = data;	/*数据段*/

        return msg;
    }


    /**
     * @brief 网络数据打包
     * @param num 设备代号
     * @param type 通讯类型
     * @param pkt 设备结构体
     * @param buf 接收缓冲区
     * @return  数据长度
     */
    public int getDataPackets(int num, int type,NetDataDomain pkt, byte[] buf){
        int len = 1 + NetConstants.DEV_FN_SIZE + 2 +pkt.len;
        NetDataCode code = getDevCode(num, (byte) type);
        NetDataPacket msg = getDataPacket(code, len, pkt);

        return setDataMsgPacket(msg, buf);
    }

    public int getTcpPackets(int num, NetDataDomain pkt, byte[] buf){ return getDataPackets(num, NetConstants.TRA_TYPR_TCP, pkt, buf); }
    public int getUdpPackets(int num, NetDataDomain pkt, byte[] buf){ return getDataPackets(num, NetConstants.TRA_TYPR_UDP, pkt, buf); }
}
