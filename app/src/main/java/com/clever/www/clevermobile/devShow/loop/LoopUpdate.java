package com.clever.www.clevermobile.devShow.loop;

import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduObjData;

import java.util.List;

/**
 * Author: lzy. Created on: 17-2-13.
 */

public class LoopUpdate {
    private PduDataPacket mDataPacket=null;
    private List<LoopItem> mLoopItemList=null;
    private LoopAdapter mAdapter=null;
    private boolean isRun = false;

    public LoopUpdate() {
        new Timers().start(500);
    }

    /**
     * 设置回路数据
     * @param adapter
     * @param list
     */
    public void setLoopData(LoopAdapter adapter, List<LoopItem> list) {
        mAdapter = adapter;
        mLoopItemList = list;
    }

    public void setDataPacket(PduDataPacket data) {
        if(!isRun) {
            isRun = true;
            mDataPacket = data;
            isRun = false;
        }
    }

    // 初始化Item内容
    private void initItem() {
        for(int i=0; i<mLoopItemList.size(); ++i) {
            LoopItem item = mLoopItemList.get(i);
            item.init();
        }
    }

    private void updateData() {
        if(mDataPacket != null) {
            if (mDataPacket.offLine > 0) {
                PduObjData loop = mDataPacket.data.loop; // 回路数据




            } else {
                initItem();
            }
            mAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 定时器
     */
    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            if(!isRun) {
                isRun = true;
                updateData();
                isRun = false;
            }
        }
    }
}
