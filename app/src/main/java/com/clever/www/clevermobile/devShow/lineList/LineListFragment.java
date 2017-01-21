package com.clever.www.clevermobile.devShow.lineList;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.login.LoginStatus;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 16-11-2.
 */
public class LineListFragment extends Fragment{
    private List<LineListItem> lineList = new ArrayList<LineListItem>();
    private LineListUpdate mLineUpdate = new LineListUpdate();
    private LineListAdapter mAlapter = null;
    private PduDataPacket mDataPacket=null;

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
        PduDataPacket dataPacket = LoginStatus.getPacket();
        mLineUpdate.setDataPacket(dataPacket);
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
}
