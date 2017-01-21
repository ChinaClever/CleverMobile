package com.clever.www.clevermobile.devShow.line;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.clever.www.clevermobile.R;

/**
 * Author: lzy. Created on: 16-12-1.
 */

public class LineCstProgress extends LinearLayout{
    private ProgressBar mProBar = null;
    private TextView mLeftTv, mDsTv, mRightTv;
    private double mMax = 100;
    public String symbol="";

    public LineCstProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_cst_progress, this);

        initView();
    }

    private void initView() {
        mProBar = (ProgressBar) findViewById(R.id.proBar);
        mLeftTv = (TextView) findViewById(R.id.leftTv);
        mDsTv = (TextView) findViewById(R.id.dsTv);
        mRightTv = (TextView) findViewById(R.id.rightTv);
    }

    public void setRange(double min, double max) {
        String minStr = (int)min+"";
        mLeftTv.setText(minStr);

        String maxStr = (int)max+"";
        mRightTv.setText(maxStr);

        mMax = max;
        mProBar.setMax((int)max);
    }

    public void setValue(double value, String sym) {
        double temp = (value/mMax) * 100;
        mProBar.setProgress((int)temp);

        String str = value + sym;
        mDsTv.setText(str);
    }

    public void setValue(double value) {setValue(value, symbol);}

    public void setColor(boolean value) {
        if(value)
            mDsTv.setTextColor(Color.RED);
        else
            mDsTv.setTextColor(Color.BLACK);
    }

    public void setString(double value) {
        mDsTv.setText(value + symbol);
    }

    public void initData() {
        mProBar.setProgress(0);
    }


}
