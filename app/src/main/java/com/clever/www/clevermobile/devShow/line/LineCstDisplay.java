package com.clever.www.clevermobile.devShow.line;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;

import static com.clever.www.clevermobile.R.id.pf;

/**
 * Author: lzy. Created on: 16-12-2.
 */

public class LineCstDisplay extends LinearLayout {
    private LineCstProgress mVol, mCur, mPow, mEle,mPf;
    private PduDataPacket mDataPacket=null;
    private int line = 0;
    private boolean isRun = false;
    private Context context;

    public LineCstDisplay(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.line_cst_dsplay, this);
        this.context = context;
        initView();
    }

    private void initView() {
        mVol = (LineCstProgress) findViewById(R.id.vol); mVol.symbol = "V";
        mVol.setOnClickListener(new ViewListener());

        mCur = (LineCstProgress) findViewById(R.id.cur); mCur.symbol = "A";
        mCur.setOnClickListener(new ViewListener());

        mPow = (LineCstProgress) findViewById(R.id.pow); mPow.symbol = "KW";
        mEle = (LineCstProgress) findViewById(R.id.ele); mEle.symbol = "KWh";
        mPf = (LineCstProgress) findViewById(pf);
        new Timers().start(500);
    }

    private int initVolSetDlg(LineSetDlg set) {
        String title = getResources().getString(R.string.line_vol);
        String sym = "V";
        PduDataUnit dataUnit = mDataPacket.data.line.vol;
        int rate = (int) RateEnum.VOL.getValue();
        set.init(title, sym, dataUnit, line, rate);

        return R.string.line_vol;
    }

    private int initCurSetDlg(LineSetDlg set) {
        String title = getResources().getString(R.string.line_cur);
        String sym = "A";
        PduDataUnit dataUnit = mDataPacket.data.line.cur;
        int rate = (int) RateEnum.CUR.getValue();
        set.init(title, sym, dataUnit, line, rate);

        return R.string.line_cur;
    }

    private void setDialog(int mode) {
        if(mDataPacket != null) {
            int strId = -1;
            View dlgView = LayoutInflater.from(context).inflate(R.layout.line_set_view, null);
            final LineSetDlg set = (LineSetDlg) dlgView.findViewById(R.id.dlg);
            if (mode == 1)
                strId = initVolSetDlg(set);
            else
                strId = initCurSetDlg(set);

            Dialog dialog = new AlertDialog.Builder(context)
                    .setTitle(strId)
                    .setView(dlgView)
                    .setPositiveButton(R.string.line_save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            set.saveData();
                        }
                    }).setNegativeButton(R.string.line_quit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();
        }
    }


    private class ViewListener implements OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.vol:
                    setDialog(1);
                    break;

                case R.id.cur:
                    setDialog(2);
                    break;
            }

        }
    }

    public void setDataPacket(PduDataPacket data) {
        if(!isRun) {
            isRun = true;
            mDataPacket = data;
            isRun = false;
        }
    }

    private void setThreshold(LineCstProgress pro,int min, int max, int value, double rate) {
        pro.setRange(min/rate, max/rate);
        pro.setValue(value/rate);
    }

    private void setDataUnit(LineCstProgress pro, PduDataUnit dataUnit, double rate) {
        int  min = dataUnit.min.get(line);
        int  max = dataUnit.max.get(line);
        int value = dataUnit.value.get(line);
        setThreshold(pro, min, max, value, rate);

        boolean alarm = false;
        if((dataUnit.alarm.get(line) == 0) || (dataUnit.crAlarm.get(line) == 0)) alarm = true;
        pro.setColor(alarm);
    }

    private void setVcData() {
        setDataUnit(mVol, mDataPacket.data.line.vol, RateEnum.VOL.getValue());
        setDataUnit(mCur, mDataPacket.data.line.cur, RateEnum.CUR.getValue());
    }


    private void setPowData() {
        int min = mDataPacket.data.line.vol.min.get(line) * mDataPacket.data.line.cur.min.get(line);
        int max = mDataPacket.data.line.vol.max.get(line) * mDataPacket.data.line.cur.max.get(line);

        int pow = mDataPacket.data.line.pow.get(line);
        setThreshold(mPow, min, max, pf, RateEnum.POW.getValue());
    }


    private void setPData() {
        setPowData();
        double ele = mDataPacket.data.line.ele.get(line) / RateEnum.ELE.getValue();
        mEle.setString(ele);

        int pf =  mDataPacket.data.line.pf.get(line);
        setThreshold(mPf, 0, 100, pf, RateEnum.PF.getValue());
    }

    private void initData() {
        mVol.initData();
        mCur.initData();
        mPf.initData();
    }

    void updateData() {
        if(mDataPacket != null) {
            if(mDataPacket.offLine > 0) {
                setVcData();
                setPData();
            } else {
                initData();
            }
        }
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            if(!isRun) {
                isRun = true;
                updateData();
                isRun = false;
            }
        }
    }

}
