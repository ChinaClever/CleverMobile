package com.clever.www.clevermobile.devShow.loop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.clever.www.clevermobile.R;

/**
 * Author: lzy. Created on: 17-2-15.
 */

public class LoopDialog extends LinearLayout{
    public LoopDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.loop_dialog, this);

    }
}
