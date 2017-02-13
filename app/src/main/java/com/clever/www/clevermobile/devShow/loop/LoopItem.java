package com.clever.www.clevermobile.devShow.loop;

/**
 * Author: lzy. Created on: 17-2-10.
 */

public class LoopItem {
    private int id = 0; // 回路名根据id号生成， id+1
    private int airSw=-1; // 0关   1开
    private double cur=-1, pow=-1;
    private int alarm=-1, crAlarm=-1;

    public LoopItem(int id) {
        this.id = id;
    }

    // 获取回路名称
    public String getName() { return "C" + (id+1); }

    public double getCur() {
        return cur;
    }

    public void setCur(double cur) {
        this.cur = cur;
    }

    public double getPow() {
        return pow;
    }

    public void setPow(double pow) {
        this.pow = pow;
    }

    public int getAirSw() {
        return airSw;
    }

    public void setAirSw(int airSw) {
        this.airSw = airSw;
    }

    public int getAlarm() {
        return alarm;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }

    public int getCrAlarm() {
        return crAlarm;
    }

    public void setCrAlarm(int crAlarm) {
        this.crAlarm = crAlarm;
    }

    public void init() {
        cur = pow = -1;
        alarm = crAlarm = -1;
    }
}
