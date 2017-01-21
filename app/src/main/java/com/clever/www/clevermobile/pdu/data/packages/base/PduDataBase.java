package com.clever.www.clevermobile.pdu.data.packages.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by lzy on 16-9-2.
 * 数据链表，PDU数据的最小组成单元
 *  1、数据使用读写锁
 *  2、使能List实现数组链表
 */
public class PduDataBase {
    private static final int DATA_MAX_SIZE = 100;
    private ReentrantReadWriteLock mLock = new ReentrantReadWriteLock(); // 读写锁
    private List<Integer> mList = new ArrayList(); // 可变数组，能快速的询问

    private void add(int data) {
        mList.add(data);
    }

    /**
     * 数组大小
     * @return
     */
    public int size() {
        return mList.size();
    }

    /**
     * 设置数组中的元素，
     * @param local 位置
     * @param data 数据
     * @return true
     */
    public boolean set(int local, int data) {
        boolean ret = true;
        if(local < DATA_MAX_SIZE) {
            while (local >= size()) { //  如果数组没这么大 会填好前面的
                add(0);
            }
            mLock.writeLock().lock(); // 上锁
            mList.set(local, data);
            mLock.writeLock().unlock(); // 解锁
        }
        else
            ret = false;

        return ret;
    }


    /**
     * 获取数据
     * @param local 位置
     * @return -1 出错
     */
    public int get(int local) {
        int ret = -1;
        if(local < size()) {
            mLock.readLock().lock();
            ret = mList.get(local).intValue();
            mLock.readLock().unlock();
        }
        return ret;
    }

    /**
     * 统一设置数据
     * @param data 数据
     */
    public void setAll(int data) {
       for (int i=0; i<mList.size(); ++i)
           mList.set(i, data);
    }
}
