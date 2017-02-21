package com.clever.www.clevermobile.devShow.output;

/**
 * Author: lzy. Created on: 16-10-8.
 * 输出位实体类，作为ListView适配器的适配类型
 */
public class Output {
    private int mId=0; // 输出位编号
    private String mName; // 输出位名称
    private int mSw = -1; // 开关状态
    private double mCur = -1, mPow = -1; // 输出电流、功率
    private boolean mCurAlarm = false, mCrAlarm = false; // 电流报警状态、临界报警

    public Output(int id) {
        mId = id;
        mName = "Output" + (id + 1);
    }

    public int getId() { return mId; }
    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public int getSw() { return mSw; }
    public void setSw(int sw) { mSw = sw; }

    public double getCur() { return mCur; }
    public void setCur(double cur) { mCur = cur; }
    public boolean getCurAlarm() { return mCurAlarm;}
    public void setCurAlarm(boolean alarm) { mCurAlarm = alarm;}

    public double getPow() { return mPow; }
    public void setPow(double pow) { mPow = pow; }

    public boolean getCrAlarm() { return mCrAlarm; }
    public void setCrAlarm(boolean alarm) { mCrAlarm = alarm; }

    public void initData() {
        mCur =  mPow = mSw = -1;
        mCurAlarm =  mCrAlarm = false;
    }
}
