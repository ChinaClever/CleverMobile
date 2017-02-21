package com.clever.www.clevermobile.devShow.lineList;

import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduObjData;

import java.util.List;

/**
 * Author: lzy. Created on: 16-11-2.
 */
public class LineListUpdate {
    private LineListAdapter mAdapter=null;
    private PduDataPacket mDataPacket=null;
    private List<LineListItem> mLineList=null;
    private boolean isRun = false;
    private static final String TAG = "LZY";

    public LineListUpdate() {
        new Timers().start(500);
    }

    public void setAdapter(LineListAdapter adapter, List<LineListItem> list) {
        mAdapter = adapter;
        mLineList = list;
    }

    public void setDataPacket(PduDataPacket data) {
        if (!isRun) {
            isRun = true;
            mDataPacket = data;
            isRun = false;
        }
    }

    private boolean getAlarm(PduDataBase dataBase, int id) {
        boolean ret = false;
        int value = dataBase.get(id);
        if(value == 1)
            ret = true;
        return ret;
    }

    private void setObjData(PduObjData objData) {
        for(int line=0; line<3; ++line) {
            int id = 0;
            LineListItem item = mLineList.get(id++);
            item.setSw(line, objData.sw.get(line));

            double value = objData.vol.value.get(line) / RateEnum.VOL.getValue();
            item = mLineList.get(id++);
            item.setValue(line, value);

            boolean alarm = getAlarm(objData.vol.alarm, line);
            item.setAlarm(line, alarm);
            alarm = getAlarm(objData.vol.crAlarm, line);
            item.setCrAlarm(line, alarm);

            value = objData.cur.value.get(line) / RateEnum.CUR.getValue();
            item = mLineList.get(id++);
            item.setValue(line, value);

            alarm = getAlarm(objData.cur.alarm, line);
            item.setAlarm(line, alarm);
            alarm = getAlarm(objData.cur.crAlarm, line);
            item.setCrAlarm(line, alarm);

            value = objData.pow.get(line) /  RateEnum.POW.getValue();
            item = mLineList.get(id++);
            item.setValue(line, value);

            value = objData.pf.get(line) / RateEnum.PF.getValue();
            item = mLineList.get(id++);
            item.setValue(line, value);

            value = objData.ele.get(line) /  RateEnum.ELE.getValue();
            item = mLineList.get(id++);
            item.setValue(line, value);
        }
    }

    private void initItemList() {
        for(int i=0; i<mLineList.size(); ++i) {
            mLineList.get(i).clearData();
        }
    }

    private void updateData() {
        if(mDataPacket != null) {
            if(mDataPacket.offLine > 0) {
                setObjData(mDataPacket.data.line);
            } else {
                initItemList();
            }
        }
        mAdapter.notifyDataSetChanged();
    }

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
