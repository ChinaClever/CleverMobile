package com.clever.www.clevermobile.devShow.env;

/**
 * Author: lzy. Created on: 16-10-27.
 */
public class EnvItem {
    private int id=0, iValue=-1;
    private String mSensorName; // 传感器名称
    private double value=-1, min=-1, max=-1;
    private boolean alarm = false;

    public EnvItem(int id,String name) {
        this.id = id;
        mSensorName = name;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getSensorName() {return mSensorName;}
    public void setSensorName(String name) {mSensorName = name;}

    public int getIValue() {return iValue;}
    public void setValue(int value) {this.iValue = value;}

    public double getValue() {return value;}
    public void setValue(double value) {this.value = value;}

    public double getMin() {return min;}
    public void setMin(double min) {this.min = min;}

    public double getMax() {return max;}
    public void setMax(double max) {this.max = max;}

    public boolean getAlarm() {return alarm;}
    public void setAlarm(boolean alarm) {this.alarm = alarm;}
    public void setAlarm(int value) {
        boolean alarm = false;
        if(value > 0)
            alarm = true;
        setAlarm(alarm);
    }

    public void init() {
        value = min = max = -1;
        alarm = false;
        iValue = -1;
    }
}
