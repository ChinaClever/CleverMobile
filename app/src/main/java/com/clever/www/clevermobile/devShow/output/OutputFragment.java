package com.clever.www.clevermobile.devShow.output;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

import static com.clever.www.clevermobile.R.id.output;

/**
 * Author: lzy. Created on: 16-11-15.
 */

public class OutputFragment extends Fragment implements AdapterView.OnItemClickListener{
    private List<Output> mOutputList = new ArrayList<Output>();
    private OutputAdapter mAapter=null;
    private PduDataPacket mDataPacket=null;
    private OutputUpdate mOutputUpdate=null;
    private int mBit = 0; // 弹出窗口用
    private OutputSetDlg mOutputSet=null;
    private static final String TAG = "LZY";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.output_fragment, container, false);

        mAapter = new OutputAdapter(getActivity(), R.layout.output_item, mOutputList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        View headView = inflater.inflate(R.layout.output_view_header, null, false);
        listView.addHeaderView(headView);

        listView.setAdapter(mAapter);
        listView.setOnItemClickListener(this); // 单击事件
        initOutput();

        mOutputUpdate = new OutputUpdate();
        mOutputUpdate.setOutputData(mAapter,mOutputList);
        updatePacketThread();

        return view;
    }


    /**
     * 初始化输出位信息，并把创建好的对象添加到列表中
     */
    private void initOutput() {
        for (int i=0; i<2; ++i)
            mOutputList.add(new Output(i)); /////=======  调试用，
    }

    private void setSwDlg() {

        String title =  mOutputList.get(mBit).getName() + getResources().getString(R.string.output_dlg_sw);
        View dlgView = LayoutInflater.from(getActivity()).inflate(R.layout.output_sw_view, null);
        final OutputSwSet outputSwSet = (OutputSwSet) dlgView.findViewById(R.id.swdlg);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setView(dlgView)
                .setPositiveButton(R.string.output_dlg_sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        outputSwSet.setSwitch(mBit+1);
                    }
                }).setNegativeButton(R.string.loop_dlg_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { // 单击输出位
        if((mDataPacket == null) || (i==0))
            return;
        Log.d(TAG, "onItemClick: " + l);

        Output output = mOutputList.get(--i);
        View dlgView = LayoutInflater.from(getActivity()).inflate(R.layout.output_dlg_view, null);
        OutputDialog dlg = (OutputDialog) dlgView.findViewById(R.id.dlg); // 自动更新对话框参数
        dlg.setData(mDataPacket, output);
        mBit  = output.getId();

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("输出位信息")
                .setView(dlgView)
                .setPositiveButton(R.string.output_dlg_threshold, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setDialog();
                    }
                }).setNeutralButton(R.string.output_dlg_sw, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setSwDlg();
                    }
                }).setNegativeButton(R.string.output_dlg_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();
    }

    private void saveOutput() {
        boolean ret = mOutputSet.saveOutputName();
        if(ret == false) {
            Toast.makeText(getActivity(), "save output name err", Toast.LENGTH_SHORT).show();
        }

        String str = mOutputSet.saveData();
        if(!str.isEmpty())
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置对话框
     */
    private void setDialog() {
        View dlgView = LayoutInflater.from(getActivity()).inflate(R.layout.output_set_view, null);
        mOutputSet = (OutputSetDlg) dlgView.findViewById(output);
        mOutputSet.setData(mDataPacket, mBit);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("输出位设置")
                .setView(dlgView)
                .setPositiveButton(R.string.output_dlg_save,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveOutput();
                            }
                        })
                .setNegativeButton(R.string.output_dlg_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        dialog.show();
    }

    private void updateDataPacket() {
        mDataPacket = LoginStatus.getPacket();
        mOutputUpdate.setDataPacket(mDataPacket);
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
