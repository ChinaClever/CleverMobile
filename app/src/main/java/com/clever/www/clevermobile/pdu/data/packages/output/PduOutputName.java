package com.clever.www.clevermobile.pdu.data.packages.output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lzy on 16-9-5.
 * 输出位名称
 */
public class PduOutputName {
    private Map<Integer, String> mMap = new HashMap<>(); //根据输出位，保存输出位名称  key 输出位， value 输出位名称

    /**
     * 设置输出位名称
     * @param output 输出位
     * @param name 输出位名称
     */
    public void set(int output, String name) {
        mMap.put(output, name);
    }

    /**
     * 获取输出位的名称
     * @param ouput 输出位
     * @return 输出位名称
     */
    public String get(int ouput) {
        return mMap.get(ouput);
    }

    /**
     * 表大小
     * @return
     */
    public int size() {
        return mMap.size();
    }

    /**
     * 获取键值列表
     * @param list 列表
     * @return 列表大小
     */
    public int getListKey(List<Integer> list){
        Set<Integer> set = mMap.keySet();
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            int num = it.next();
            list.add(num);
        }
        return list.size();
    }


    /**
     * 统一设置输出位
     * @param name
     */
    public void setAll(String name) {
        List<Integer> list = new ArrayList<>();
        int ret = getListKey(list);
        for(int i=0; i<ret; ++i){
            int num = list.get(i);
            String str = name+" " +num;
            set(num, str);
        }
    }
}
