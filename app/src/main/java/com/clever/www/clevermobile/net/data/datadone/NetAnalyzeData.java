package com.clever.www.clevermobile.net.data.datadone;

import com.clever.www.clevermobile.net.data.packages.NetDataCode;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.net.data.packages.NetDataPacket;

/**
 * Created by lzy on 16-9-8.
 * 接收到的网络数据，还原成协议数据包
 * 公共接口：netDataAnalytic()
 */
public class NetAnalyzeData {

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
     *  数据包同步 查检数据包是否完整
     * @return >= 0成功
     */
    private int dataPacketSync(byte[] buf, int len){
        int ret=0, offset=0;
        byte[] head = {NetConstants.DATA_MSG_HDR, NetConstants.DATA_MSG_STX}; /*包头*/

        if(len < 2+NetConstants.DATA_MSG_CODE_SIZE+2) /*数据包不完整*/
            return NetConstants.DATA_ERR_ABNORMAL;
        else if(len > NetConstants.DATA_MSG_SIZE)
            return NetConstants.DATA_ERR_LEN;

    /*查找同步头*/
        do {
            if(offset < len-10) {
                if((buf[offset] != head[0]) || (buf[offset+1] != head[1])) {  /*查找包头*/
                    ret = -1;
                    offset++; /*偏量增加*/
                } else
                    ret = 0;
            } else
                return NetConstants.DATA_ERR_HEAD; /*同步头错误*/
        } while(ret!=0);

    /*验证包尾*/
        int rtn = offset + 2 + NetConstants.DATA_MSG_CODE_SIZE; /*代号段结束位置*/
        rtn += (buf[rtn]<<8) + buf[rtn+1]; /*数据长度*/
        rtn += 2+1; /*数据长度二字节、校验位一字节*/
        if(rtn < len){
            if(buf[rtn] == NetConstants.DATA_MSG_ED) /**/
                return offset; /*返回头位置*/
        }

        return NetConstants.DATA_ERR_END;/*结束符未找到*/
    }


    /**
     * 解析代号段数据
     * @param buf 数组
     * @param code 代号段结构体
     * @return 代号段长度
     */
    private int getPacketCode(byte[] buf, int ptr, NetDataCode code) {
        for(int i=0; i<NetConstants.DEV_CODE_SIZE; ++i)
            code.devCode[i] = buf[ptr++]; /*设备代号*/

        code.type = buf[ptr++]; /*通讯类型*/
        code.version = buf[ptr++]; /*版本号*/
        code.trans = buf[ptr++]; /*服务端发送标志：10H 客户端应答标志：03H */
        code.reserve = 0;

        return NetConstants.DATA_MSG_CODE_SIZE;
    }


    /**
     * 功能：解析设备数据包
     * 入口参数：buf->数据首地址 	len->数据长度
     * 出口参数：pkt->打包之后MSG
     * 返回:TRUE
     */
    private int dataDomainAnalytic(byte[] buf, int ptr, int len, NetDataDomain pkt) {
        pkt.addr = buf[ptr++];/*获取源地址码*/
        for(int i=0; i<NetConstants.DEV_FN_SIZE; ++i) /*功能码*/
            pkt.fn[i] = buf[ptr++];

        int rtn = buf[ptr] * 256 + buf[ptr+1]; /*数据长度*/
        if(rtn < len) {
            pkt.len = rtn;
            ptr += 2;

            for(int i=0; i<rtn; ++i) { /*数据区*/
                pkt.data.add(buf[ptr++]);
            }


        }  else {
            rtn = NetConstants.DATA_ERR_DATA_LEN; // 数据域长度错误
        }

        return rtn;
    }


    /**
     * 解析数据包的数据域
     * @param buf 数组
     * @param ptr 偏移量
     * @param msg 结构体
     * @return 长度
     */
    private int getPacketDomain(byte[] buf, int ptr, NetDataPacket msg) {

        int len = (buf[ptr]*256) + buf[ptr+1]; /*获取数据长度*/
        msg.len = len;
        ptr += 2;

        if((len >= 0) && (len < NetConstants.DATA_MSG_SIZE)) {
            msg.XOR = buf[ptr+len];
            byte XOR = getXOR(buf,ptr,len); // 检查校验码
            //XOR = msg->XOR;  // 不检查校验码 为了提高执行效率  校验码不检查
            if(XOR == msg.XOR) {
                int ret = dataDomainAnalytic(buf,ptr, len, msg.dataDomain);  /* 数据段*/
                if(ret <= 0)
                    return ret;
            } else {
                return NetConstants.DATA_ERR_XOR;
            }
        } else  {
            return NetConstants.DATA_MSG_SIZE;
        }

        return (2+len+1);
    }


    /**
     * 解析数据包
     * @param buf 接收数据缓冲区
     * @param len 接收到数据长度
     * @param msg 包结构体
     * @return 长度 > 0 正确
     */
    public int netDataAnalytic(byte[] buf, int len, NetDataPacket msg)  {
        int ret, ptr=0;
        ret = dataPacketSync(buf,len);
        if(ret >= 0) {
            ptr += ret;
            msg.hdr = buf[ptr++]; /*信息头*/
            msg.stx = buf[ptr++]; /*标识字*/

            ptr += getPacketCode(buf,ptr,msg.code); /*获取代号段数据*/
            ret = getPacketDomain(buf, ptr, msg);	/*获取数据段*/
            if(ret >= 0)
            {
                ptr += ret;
                msg.ED = buf[ptr];

                return (2+NetConstants.DATA_MSG_CODE_SIZE+2+msg.len+2); /*整个包的长度*/
            }
        }
        return ret;
    }


}
