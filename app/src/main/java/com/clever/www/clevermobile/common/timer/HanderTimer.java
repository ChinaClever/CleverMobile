package com.clever.www.clevermobile.common.timer;

import android.os.Handler;

/**
 * Author: lzy. Created on: 16-11-4.
 * 定时器类
 */
public abstract class HanderTimer {
    Handler handler = new Handler();
    private int time = 1000;

    public void start(int t) {
        time = t;
        handler.postDelayed(runnable, time); //每隔1s执行
    }

    public void stop() {
        handler.removeCallbacks(runnable);// 关闭定时器处理
    }

    public abstract void timeout();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                handler.postDelayed(this, time);  // handler自带方法实现定时器
                timeout();
            } catch (Exception e) { // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };

}
