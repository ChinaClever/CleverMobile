package com.clever.www.clevermobile.devShow.env;

import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduEnvData;

import java.util.List;

/**
 * Author: lzy. Created on: 16-10-28.
 */
public class EnvUpdate {
    private EnvAdapter mAdapter=null;
    private PduDataPacket mDataPacket=null;
    private List<EnvItem> mEnvList=null;
    private boolean isRun = false;

    public EnvUpdate() {
        new Timers().start(500);
    }

    public void setEnvData(EnvAdapter adapter, List<EnvItem> list) {
        mAdapter = adapter;
        mEnvList = list;
    }

    public void setDataPacket(PduDataPacket data) {
        if(!isRun) {
            isRun = true;
            mDataPacket = data;
            isRun = false;
        }
    }

    void setDataUnit(PduDataUnit dataUnit, int base, double rate) {
        for (int i=0; i<4; ++i) {
            EnvItem envItem = mEnvList.get(i+base);
            double value = dataUnit.value.get(i) / rate;
            envItem.setValue(value);

            value = dataUnit.min.get(i) / rate;
            envItem.setMin(value);

            value = dataUnit.max.get(i) / rate;
            envItem.setMax(value);

            envItem.setAlarm(dataUnit.alarm.get(i));
        }
    }

    void updateOther(PduDataBase dataBase, int len, int base) {
        for(int i=0; i<len; ++i) {
            int value = dataBase.get(i);
            EnvItem envItem = mEnvList.get(i+base);
            envItem.setValue(value);
        }
    }

    void initItem() {
        for(int i=0; i<mEnvList.size(); ++i) {
            EnvItem envItem = mEnvList.get(i);
            envItem.init();
        }
    }

    void updateData() {
        if(mDataPacket != null) {
            if(mDataPacket.offLine > 0) {
                PduEnvData envData = mDataPacket.data.env;

                int base = 0;
                setDataUnit(envData.tem, base, RateEnum.TEM.getValue());
                base += 4;

                setDataUnit(envData.hum, base, RateEnum.HUM.getValue());
                base += 4;

                updateOther(envData.door, 2, base);
                base += 2;

                updateOther(envData.water, 1, base);
                base += 1;

                updateOther(envData.smoke, 1, base);
            } else {
                initItem();
            }
            mAdapter.notifyDataSetChanged();
        }
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
