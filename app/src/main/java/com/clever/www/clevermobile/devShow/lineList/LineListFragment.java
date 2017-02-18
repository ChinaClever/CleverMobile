package com.clever.www.clevermobile.devShow.lineList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.devShow.line.LineSetDlg;
import com.clever.www.clevermobile.login.LoginStatus;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 16-11-2.
 */
public class LineListFragment extends Fragment implements AdapterView.OnItemClickListener  {
    private List<LineListItem> lineList = new ArrayList<LineListItem>();
    private LineListUpdate mLineUpdate = new LineListUpdate();
    private LineListAdapter mAlapter = null;
    private PduDataPacket mDataPacket = null;
    private String mLineList[] = new String[]{"Line 1", "Line 2", "Line 3"};
    private int mChNum=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.line_list_fragment, container, false);

        initLineItem();
        mAlapter = new LineListAdapter(getActivity(), R.layout.line_list_item, lineList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        View headView = inflater.inflate(R.layout.line_view_header, null, false);
        listView.addHeaderView(headView);

        listView.setAdapter(mAlapter);
        listView.setOnItemClickListener(this);
        mLineUpdate.setAdapter(mAlapter, lineList);
        updatePacketThread();

        return view;
    }

    /**
     * 初始化相
     */
    private void initLineItem() {
        int id = 0;
        String str = getResources().getString(R.string.line_list_sw);
        LineListItem item = new LineListItem(id++, str);
        lineList.add(item);

        str = getResources().getString(R.string.line_list_vol);
        item = new LineListItem(id++, str);
        lineList.add(item);

        str = getResources().getString(R.string.line_list_cur);
        item = new LineListItem(id++, str);
        lineList.add(item);

        str = getResources().getString(R.string.line_list_pow);
        item = new LineListItem(id++, str);
        lineList.add(item);

        str = getResources().getString(R.string.line_list_pf);
        item = new LineListItem(id++, str);
        lineList.add(item);

        str = getResources().getString(R.string.line_list_ele);
        item = new LineListItem(id++, str);
        lineList.add(item);
    }

    private void updateDataPacket() {
        mDataPacket = LoginStatus.getPacket();
        mLineUpdate.setDataPacket(mDataPacket);
    }

    private void updatePacketThread() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        updateDataPacket();
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    private int initVolSetDlg(LineSetDlg set) {
        String title = getResources().getString(R.string.line_vol);
        String sym = "V";
        PduDataUnit dataUnit = mDataPacket.data.line.vol;
        int rate = (int) RateEnum.VOL.getValue();
        set.init(title, sym, dataUnit, mChNum, rate);

        return R.string.line_vol;
    }

    private int initCurSetDlg(LineSetDlg set) {
        String title = getResources().getString(R.string.line_cur);
        String sym = "A";
        PduDataUnit dataUnit = mDataPacket.data.line.cur;
        int rate = (int) RateEnum.CUR.getValue();
        set.init(title, sym, dataUnit, mChNum, rate);

        return R.string.line_cur;
    }

    private void setDialog(int mode) {
        if(mDataPacket != null) {
            int strId = -1;
            View dlgView = LayoutInflater.from(getActivity()).inflate(R.layout.line_set_view, null);
            final LineSetDlg set = (LineSetDlg) dlgView.findViewById(R.id.dlg);
            if (mode == 2)
                strId = initVolSetDlg(set);
            else
                strId = initCurSetDlg(set);
            String title = mLineList[mChNum] + getResources().getString(strId);

            Dialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(title)
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 2 || i == 3) {
            final int line = i;
            Dialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.line_dlg_title)
                    .setPositiveButton(R.string.line_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setDialog(line);
                        }
                    }).setNegativeButton(R.string.line_quit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setSingleChoiceItems(mLineList, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mChNum = i;
                        }
                    }).create();
            dialog.show();
        }
    }
}
