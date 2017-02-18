package com.clever.www.clevermobile.devShow.loop;

import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;
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

    private void setDataUnit(PduDataUnit dataUnit, double rate) {
        for (int i=0; i<mLoopItemList.size(); ++i) {
            LoopItem item = mLoopItemList.get(i);
            double value = dataUnit.value.get(i) / rate;
            item.setCur(value);

            item.setAlarm(dataUnit.alarm.get(i));
            item.setCrAlarm(dataUnit.crAlarm.get(i));
        }
    }

    private void setObjData(PduObjData objData) {
        double powRate = RateEnum.POW.getValue();
        double curRate = RateEnum.CUR.getValue();

        setDataUnit(objData.cur, curRate); // 设置电流
        for (int i=0; i<mLoopItemList.size(); ++i) {
            LoopItem item = mLoopItemList.get(i);
            item.setAirSw(objData.sw.get(i));  // 设置开关

            item.setPow(objData.pow.get(i)/powRate); // 设置功率
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
                setObjData(loop);
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
