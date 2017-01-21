package com.clever.www.clevermobile.pdu.data.hash.data;

import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 16-9-13.
 * PDU设备Hash表
 *  主要以设备号，保存设备数据
 */
public class PduDevHash {
    private Map<Integer, PduDataPacket> mMap = new HashMap<>();
    public boolean isNew = false; // 是否是新设备

    public int size() {
        return mMap.size();
    }

    public void del(int num){
        mMap.remove(num);
    }

    /**
     * 获取设备数据包，
     * @param num 设备号
     * @return 没有则返回空
     */
    public PduDataPacket getPacket(int num) {
        return mMap.get(num);
    }

    private PduDataPacket add(int num) {
        PduDataPacket dataPacket = new PduDataPacket();
        mMap.put(num, dataPacket);
        return dataPacket;
    }

    /**
     * 获取设备数据包，没有则创建
     * @param num 设备号
     * @return
     */
    public PduDataPacket get(int num) {
        PduDataPacket dataPacket = getPacket(num);
        if(dataPacket == null) {
            dataPacket = add(num);
            isNew = true;
        } else {
            isNew = false;
        }

        return dataPacket;
    }

    /**
     * 获取主机的数据包
     * @return
     */
    public PduDataPacket getMaster() {
        return getPacket(0);
    }

    /**
     * 列出所有设备号
     * @return 设备数量
     */
    public int list(List<Integer> list) {
        for (Integer key : mMap.keySet()) {
            list.add(key);
        }

        return list.size();
    }


}
