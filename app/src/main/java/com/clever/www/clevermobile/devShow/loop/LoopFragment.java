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
    private LoopUpdate mLoopUpdate = new LoopUpdate();

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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LoopItem loopItem = mLoopItemList.get(--i);

        String title = loopItem.getName() + getResources().getString(R.string.loop_list_name);

        View dlgView =  LayoutInflater.from(getActivity()).inflate(R.layout.loop_dlg_view, null);
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(dlgView)
                .setPositiveButton(R.string.loop_dlg_threshold, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNeutralButton(R.string.loop_dlg_sw, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(R.string.loop_dlg_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();



    }
}
