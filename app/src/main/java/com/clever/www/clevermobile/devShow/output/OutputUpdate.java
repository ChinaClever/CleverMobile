package com.clever.www.clevermobile.devShow.output;

import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

import java.util.List;

import static com.clever.www.clevermobile.R.id.sw;

/**
 * Author: lzy. Created on: 16-10-11.
 * 输出位更新类
 */
public class OutputUpdate {
    private List<Output> mOutputList = null;
    private OutputAdapter mAapter=null;
    private PduDataPacket mDataPacket = null;
    private boolean isRun = false;

    public OutputUpdate() {
        new Timers().start(500);
    }

    /**
     * 设置输出位数据
     */
    public void setOutputData(OutputAdapter adapter,List<Output> OutputList) {
        mAapter = adapter;
        mOutputList = OutputList;
    }

    public void setDataPacket(PduDataPacket data) {
        if(!isRun) {
            isRun = true;
            mDataPacket = data;
            isRun = false;
        }
    }

    /**
     * 自动调整输出位数量，
     */
    private void addOutputItem() {
        int len = mDataPacket.data.output.cur.value.size();
        if(mOutputList.size() < len) { // 没有显示这么多输出位
            for(int i = mOutputList.size(); i<len; ++i) {
                mOutputList.add(new Output(i));
            }
        }
    }

    private void autoOutputItem() {
        int len = mDataPacket.data.output.cur.value.size();
        if(mOutputList.size() != len) {
            mOutputList.clear();
            addOutputItem();
        }
    }

    /**
     * 设置输出位的名称
     */
    private void setOutputName() {
        for(int i = 0; i<mOutputList.size(); ++i) {
            String name = mDataPacket.output.name.get(i);
            if(name == null)
                name = "Output"+(i+1);
            mOutputList.get(i).setName(name);
        }
    }

    /**
     * 设置输出位的开关状态
     */
    private void setOutputSw() {
        for(int i = 0; i<mOutputList.size(); ++i) {
            int sw= mDataPacket.data.output.sw.get(i);
            mOutputList.get(i).setSw(sw);
        }
    }


    /**
     * 设置输出位电流信息
     */
    private void setOutputCur() {
        for(int i = 0; i<mOutputList.size(); ++i) {
            float cur = (float)(mDataPacket.data.output.cur.value.get(i)/ RateEnum.CUR.getValue());
            mOutputList.get(i).setCur(cur);

            boolean alarm = false;
            int ret = mDataPacket.data.output.cur.alarm.get(i);
            if(ret == 1)
                alarm = true;
            mOutputList.get(i).setCurAlarm(alarm);
        }
    }

    /**
     * 输出位功率
     */
    private void setOutputPow() {
        for(int i = 0; i<mOutputList.size(); ++i) {
            float pow = (float) (mDataPacket.data.output.pow.get(i) / RateEnum.POW.getValue());
            mOutputList.get(i).setPow(pow);
        }
    }


    private void setOutputCrAlarm() {
        for(int i = 0; i<mOutputList.size(); ++i) {
            boolean alarm = false;
            int ret = mDataPacket.data.output.cur.crAlarm.get(i);
            if (ret == 1)
                alarm = true;
            mOutputList.get(i).setCrAlarm(alarm);
        }
    }

    /**
     * 初始化输出位信息
     */
    private void initOutputItem() {
        for(int i = 0; i<mOutputList.size(); ++i) {
            mOutputList.get(i).initData();
        }
    }


    /**
     * 更新输出位状态信息
     */
    public void updateOutput() {
        if((mDataPacket != null) && (mOutputList != null)){
            if(mDataPacket.offLine > 0) {
                autoOutputItem(); // 调整输出位数
                setOutputName(); // 输出位名称
                setOutputSw(); // 输出位开关
                setOutputCur(); //输出位电流信息
                setOutputPow(); // 输出位功率信息
                setOutputCrAlarm(); // 临界状态
            }
            else
                initOutputItem();

            mAapter.notifyDataSetChanged();  //调用adapter的通知方法告诉listview数据已经改变
        }
    }


    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            if(!isRun) {
                isRun = true;
                updateOutput();
                isRun = false;
            }
        }
    }
}
