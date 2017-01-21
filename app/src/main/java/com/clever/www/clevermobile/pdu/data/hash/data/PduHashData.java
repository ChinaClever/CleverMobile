package com.clever.www.clevermobile.pdu.data.hash.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 16-9-13.
 * PDU哈希表
 * 以设备类型为键值
 */
public class PduHashData {
    private static Map<Integer, PduHashIP> mMap = new HashMap<>();

    public int size() {
        return mMap.size();
    }

    public void del(int type){
        mMap.remove(type);
    }

    /**
     * 根据设备类型来获取设备
     * @param type 设备类型
     * @return
     */
    public PduHashIP getHash(int type) {
        return mMap.get(type);
    }

    private PduHashIP add(int type) {
        PduHashIP pdu = new PduHashIP();
        mMap.put(type, pdu);
        return pdu;
    }

    /**
     * 获取设备哈希，不存在则会创建
     * @param type 设备类型
     * @return
     */
    public PduHashIP get(int type) {
        PduHashIP pdu = getHash(type);
        if(pdu == null) {
            pdu = add(type);
        }
        return pdu;
    }


    /**
     * 列出所有设备类型
     * @param list 设备类型链表
     * @return 数量
     */
    public int list(List<Integer> list) {
        for (Integer key : mMap.keySet()) {
            list.add(key);
        }

        return list.size();
    }
}
