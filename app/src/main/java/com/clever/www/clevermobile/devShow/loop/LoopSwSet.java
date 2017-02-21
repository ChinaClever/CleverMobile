package com.clever.www.clevermobile.devShow.loop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.devShow.set.SetDevCom;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 17-2-20.
 */

public class LoopSwSet extends LinearLayout implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup mSwRadio = null;
    private int  mLoopId=1, mSw = 1; // 开关

    public LoopSwSet(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.loop_sw_set, this);

        mSwRadio = (RadioGroup) findViewById(R.id.sw);
        mSwRadio.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(group == mSwRadio) {
            switch (checkedId) {
                case R.id.open:
                    mSw = 1;
                    break;

                case R.id.close:
                    mSw = 0;
                    break;
            }
        }
    }

    public void setLoopId(int id) {
        mLoopId = id;
    }

    private boolean setLoopSw() {
        SetDevCom setDevCom = SetDevCom.get();

        List<Integer> list = new ArrayList<>();
        list.add(mSw);

        NetDataDomain pkt = new NetDataDomain();
        pkt.fn[0] = 0x72;
        pkt.fn[1] = (byte) mLoopId;
        pkt.len = setDevCom.intToByteList(list, pkt.data);

        return setDevCom.setDevData(pkt, false);
    }
    /**
     * 开关设置
     */
    public void setSwitch() {
        setLoopSw();
    }

}
