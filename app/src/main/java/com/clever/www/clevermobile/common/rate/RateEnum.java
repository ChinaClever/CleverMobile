package com.clever.www.clevermobile.common.rate;

/**
 * Author: lzy. Created on: 16-11-16.
 */

public enum RateEnum {
    VOL(10), CUR(10),POW(1000), ELE(10), PF(100), TEM(10), HUM(10);

    private double value;
    private RateEnum(double value) { this.value = value; }
    public double getValue() {  return value; }
}
