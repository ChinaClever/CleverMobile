package com.clever.www.clevermobile.pdu.data.packages.base;

/**
 * Created by lzy on 16-9-2.
 * 字符串基类，字符串最小组成单元
 *  1、字符串使用读写锁
 *  2、修改之前，先比较字符串是否需要修改，字符串不相等才修改
 */
public class PduStrBase {
    private StringBuilder mStr = new StringBuilder();

    public void clear() {
        mStr.setLength(0);
    }

    /**
     * 设置字符串
     * @param str
     */
    public void set(String str) {
        if(!mStr.equals(str)) {
            clear();
            mStr.append(str);
        }
    }

    public void set(StringBuilder str) {
        if(!mStr.equals(str)) {
            clear();
            mStr.append(str);
        }
    }

    /**
     * 获取字符串
     * @return
     */
    public String get() {
        return mStr.toString();
    }

}
