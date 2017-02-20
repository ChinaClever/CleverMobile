package com.clever.www.clevermobile.devShow.lineList;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 16-11-2.
 */
public class LineListItem {
    private static final int LINE_NUM = 3;
    private int id=-1;
    private String name;

    private List<Boolean> swList = new ArrayList<>();
    private List<Double> valueList = new ArrayList<>();
    private List<Boolean> alarmList = new ArrayList<>();
    private List<Boolean> crAlarmList = new ArrayList<>();

    public LineListItem(int id, String name) {
        this.id = id;
        this.name = name;
        initData();
    }

    private void initData() {
        for(int i=0; i<LINE_NUM; ++i) {
            swList.add(true);
            valueList.add((double) -1);
            alarmList.add(false);
            crAlarmList.add(false);
        }
    }

    public int getId() {return  id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) { this.name = name; }

    public void setValue(int line, double value) { valueList.set(line, value); }
    public double getValue(int line) { return valueList.get(line); }

    public void setAlarm(int line, boolean value) { alarmList.set(line, value); }
    public Boolean getAlarm(int line) { return alarmList.get(line); }

    public void setCrAlarm(int line, boolean value) { crAlarmList.set(line, value); }
    public boolean getCrAlarm(int line) { return crAlarmList.get(line); }

    public void setSw(int line, int value) {
        boolean ret = true;
        if(value == 0)
            ret = false;
        setSw(line, ret);
    }
    public void setSw(int line, boolean value) {swList.set(line, value); }
    public boolean getSw(int line) { return swList.get(line); }

    public void clearData() {
        for(int i=0; i<LINE_NUM; ++i) {
            swList.set(i, true);
            valueList.set(i, (double) -1);
            alarmList.set(i, false);
            crAlarmList.set(i, false);
        }
    }
}
