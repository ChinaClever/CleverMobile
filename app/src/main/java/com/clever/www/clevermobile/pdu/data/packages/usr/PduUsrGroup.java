package com.clever.www.clevermobile.pdu.data.packages.usr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduUsrGroup {
    private Map<String, Integer> mHash = new HashMap<>();

    /**
     * @brief 清空
     */
    public void del() { mHash.clear(); }

    /**
     * @brief 删除组
     */
    public void del(String group) { mHash.remove(group); }

    /**
     * @brief 获取组信息
     */
    public int get(String group) { return mHash.get(group); }


    /**
     * @brief 增加或修改组
     * @param group
     * @param data
     */
    public void set(String group, int data) { mHash.put(group, data); }


    /**
     * @brief 大小
     * @return
     */
    public int size() {  return mHash.size(); }

    /**
     * @brief 获取组列表
     * @return
     */
    public int getGroup(List<String> list) {
        Set<String> set = mHash.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String name = it.next();
            list.add(name);
        }
        return list.size();
    }


}
