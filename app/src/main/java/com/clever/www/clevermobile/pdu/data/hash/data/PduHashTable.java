package com.clever.www.clevermobile.pdu.data.hash.data;

/**
 * Created by lzy on 16-9-14.
 * PDU设备Hash表
 *  此Hash为全局变量
 */
public class PduHashTable {
    private static PduHashData mHashData = new PduHashData();

    /**
     * 获取表态哈希
     * @return
     */
    public static PduHashData getHash() {
        return mHashData;
    }


}
