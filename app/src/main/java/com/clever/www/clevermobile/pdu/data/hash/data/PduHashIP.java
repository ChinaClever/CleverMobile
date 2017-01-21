package com.clever.www.clevermobile.pdu.data.hash.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lzy on 16-9-13.
 * PDU设备Hash表
 *  主要以设备IP，保存PduDevHash对象
 */
public class PduHashIP {
    private Map<String, PduDevHash> mMap = new HashMap<>();

    public int size() {
        return mMap.size();
    }

    public void del(String ip){
        mMap.remove(ip);
    }

    /**
     * 获取设备数据，
     * @param ip 设备ip
     * @return 没有则返回空
     */
    public PduDevHash getDev(String ip) {
        return mMap.get(ip);
    }

    private PduDevHash add(String ip) {
        PduDevHash dev = new PduDevHash();
        mMap.put(ip, dev);
        return dev;
    }

    /**
     * 获取设备数据，没有则创建
     * @return
     */
    public PduDevHash get(String ip) {
        PduDevHash dev = getDev(ip);
        if(dev == null) {
            dev = add(ip);
        }
        return dev;
    }

    /**
     * 列出所有设备IP
     * @return 设备数量
     */
    public int list(List<String> list) {
        for (String key : mMap.keySet()) {
            list.add(key);
        }

        return list.size();
    }
}
