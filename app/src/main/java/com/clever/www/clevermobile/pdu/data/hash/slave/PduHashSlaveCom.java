package com.clever.www.clevermobile.pdu.data.hash.slave;

import com.clever.www.clevermobile.net.data.datadone.NetConstants;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;
import com.clever.www.clevermobile.pdu.data.packages.base.PduStrBase;

import java.util.List;

/**
 * Created by lzy on 16-9-18.
 * 公共接口
 */
public class PduHashSlaveCom {
    private static int[] mIntArray = new int[NetConstants.DATA_MSG_SIZE];
    private static byte[] mByteArray = new byte[NetConstants.DATA_MSG_SIZE];

    /**
     * 检查传输类型、数据来源
     * @param type 传输类型
     * @param trans 传输来源
     * @return true
     */
    public boolean checkTranType(byte type, byte trans) {
        boolean ret = false;
        if((type == NetConstants.TRA_TYPR_TCP) || (type == NetConstants.TRA_TYPR_UDP)) {
            if(trans == NetConstants.DATA_MSG_CLIENT)
                ret = true;
        }
        return ret;
    }

    /**
     * 把byte型数组中的设备类型号 转化成int数据
     * @param devCodeArray
     * @return 设备类型号
     */
    public int getDevCode(byte[] devCodeArray) {
        int devCode = 0;
        for(int i=0; i<NetConstants.DEV_CODE_SIZE; ++i){
            devCode <<= 8;
            devCode += devCodeArray[i];
        }
        return devCode;
    }

    private int byteToInt(int[] dest, int len, List<Byte> buf, int size){
        int i=0,j=0,k=0,temp=0;
        if(len%size == 0){ //必须是偶数
            for(i=0; i<len/size; ++i){
                temp = 0;
                for(j=0; j<size; ++j) {
                    temp <<= 8;
                    temp += buf.get(k++) & 0xff;
                }
                dest[i] = temp;
            }
        }
        else  {
            len = 0;
        }

        return len/size;
    }


    public void saveHashIntData(PduDataBase ptr, int len, List<Byte> data, int sizeBit){
        int[] buf = mIntArray;
        int rtn = byteToInt(buf, len, data, sizeBit); // 数据转化
        if(rtn > 0) {
            for(int i=0; i<rtn; ++i)
                ptr.set(i, buf[i]); //数据保存
        }
    }


    public String charToString(List<Byte> data, int len) {
        byte[] strBuf = mByteArray;
        for(int i=0; i<len; ++i)
            strBuf[i] = data.get(i);
        String str = new String(strBuf,0, len);
        strBuf = null;

        return str;
    }


    /**
     * @brief 保存字符串数据
     * @param len
     * @param data
     */
    private void saveString(PduStrBase strBase, int len, List<Byte> data) {
        String str = charToString(data,len);
        strBase.set(str);
        str = null;
    }

    /**
     * @brief 保存字符串数据
     * @param data
     */
    public void devStrSave(PduStrBase strBase, NetDataDomain data) {
        saveString(strBase, data.len, data.data);
    }

}
