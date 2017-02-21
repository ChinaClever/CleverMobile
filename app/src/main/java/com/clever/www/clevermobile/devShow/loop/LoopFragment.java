package com.clever.www.clevermobile.devShow.loop;


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
import android.widget.Toast;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.login.LoginStatus;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 17-2-10.
 */

public class LoopFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<LoopItem> mLoopItemList = new ArrayList<>();
    private PduDataPacket mDataPacket=null;
    private LoopUpdate mLoopUpdate = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loop_fragment,container, false);

        initLoop();
        LoopAdapter adapter = new LoopAdapter(getActivity(), R.layout.loop_item, mLoopItemList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        View headView = inflater.inflate(R.layout.loop_view_header, null, false);
        listView.addHeaderView(headView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        mLoopUpdate = new LoopUpdate();
        mLoopUpdate.setLoopData(adapter, mLoopItemList);

        updatePacketThread();

        return view;
    }

    /**
     * 初始化回路界面
     */
    private void initLoop() {
        for(int i=0; i<6; ++i) {
            mLoopItemList.add(new LoopItem(i));
        }
    }

    private void updateDataPacket() {
        mDataPacket = LoginStatus.getPacket();
        mLoopUpdate.setDataPacket(mDataPacket);
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


    /**
     * 阈值设置对话框
     * @param loopId
     */
    private void setThresholdDlg(int loopId) {
        String title = "C" + (loopId+1) + getResources().getString(R.string.loop_dlg_threshold);
        View dlgView =  LayoutInflater.from(getActivity()).inflate(R.layout.loop_set_view, null);
        final LoopSetDlg loopSetDlg = (LoopSetDlg) dlgView.findViewById(R.id.dlg);
        loopSetDlg.setData(mDataPacket, loopId);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(dlgView)
                .setPositiveButton(R.string.loop_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str = loopSetDlg.saveData();
                        if(!str.isEmpty())
                            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(R.string.loop_dlg_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();
    }

    private void setSwDlg(int loopId) {
        String title =  "C" + (loopId+1) + getResources().getString(R.string.loop_dlg_sw);
        View dlgView = LayoutInflater.from(getActivity()).inflate(R.layout.loop_sw_view, null);
        final LoopSwSet loopSwSet = (LoopSwSet) dlgView.findViewById(R.id.swdlg);
        loopSwSet.setLoopId(loopId+1);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(dlgView)
                .setPositiveButton(R.string.loop_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loopSwSet.setSwitch();
                    }
                }).setNegativeButton(R.string.loop_dlg_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int loopId=0;
        if(i>0)  loopId = i; else return;
        LoopItem loopItem = mLoopItemList.get(--loopId);

        String title = loopItem.getName() + getResources().getString(R.string.loop_list_name);
        View dlgView =  LayoutInflater.from(getActivity()).inflate(R.layout.loop_dlg_view, null);
        LoopDialog loopDialog = (LoopDialog) dlgView.findViewById(R.id.dlg);
        loopDialog.setData(mDataPacket, loopId);

        final int finalLoopId = loopId;
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(dlgView)
                .setPositiveButton(R.string.loop_dlg_threshold, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setThresholdDlg(finalLoopId);
                    }
                }).setNeutralButton(R.string.loop_dlg_sw, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setSwDlg(finalLoopId);
                    }
                }).setNegativeButton(R.string.loop_dlg_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();
    }
}
