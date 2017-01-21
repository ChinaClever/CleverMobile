package com.clever.www.clevermobile.devShow.env;

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
import com.clever.www.clevermobile.common.rate.RateEnum;
import com.clever.www.clevermobile.login.LoginStatus;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: lzy. Created on: 16-10-26.
 */
public class EnvFragment extends Fragment implements AdapterView.OnItemClickListener {
    private List<EnvItem> mEnvList = new ArrayList<EnvItem>();
    private PduDataPacket mDataPacket=null;
    private EnvUpdate mEnvUpdate = new EnvUpdate();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.env_fragment, container, false);

        initEnv();
        EnvAdapter adapter = new EnvAdapter(getActivity(), R.layout.env_item, mEnvList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);

        View headView = inflater.inflate(R.layout.env_view_header, null, false);
        listView.addHeaderView(headView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        mEnvUpdate.setEnvData(adapter, mEnvList);
        updatePacketThread();

        return view;
    }

    private void initEnv() {
        for(int i=0; i<4; ++i) {
            EnvItem env = new EnvItem(i, getResources().getString(R.string.env_tem)+ (i+1));
            mEnvList.add(env);
        }

        for(int i=4; i<8; ++i) {
            EnvItem env = new EnvItem(i, getResources().getString(R.string.env_hum) + (i-3));
            mEnvList.add(env);
        }

        for(int i=8; i<10; ++i) {
            EnvItem env = new EnvItem(i, getResources().getString(R.string.env_door) + (i-7));
            mEnvList.add(env);
        }

        EnvItem env = new EnvItem(10, getResources().getString(R.string.env_water));
        mEnvList.add(env);

        env = new EnvItem(11, getResources().getString(R.string.env_smoke));
        mEnvList.add(env);
    }

    private int getDlgTitle(EnvItem env) {
        int strId=-1;

        int id = env.getId();
        if(id < 4) {
            strId = R.string.env_tem;
        } else if(id < 8) {
            strId = R.string.env_hum;
        }
        return strId;
    }

    private void initCstEnvSet(int id, EnvSetDlg set) {
        if(id < 4) { // 温度
            String title = getResources().getString(R.string.env_tem);
            String sym = "°C";
            PduDataUnit dataUnit = mDataPacket.data.env.tem;
            int rate = (int) RateEnum.TEM.getValue();
            set.init(title, sym, dataUnit, id, rate);
        } else if(id < 8) {
            String title = getResources().getString(R.string.env_hum);
            String sym = "%";
            PduDataUnit dataUnit = mDataPacket.data.env.hum;
            id -= 4;
            int rate = (int) RateEnum.HUM.getValue();
            set.init(title, sym, dataUnit, id,rate);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if((mDataPacket == null) || (i==0))
            return;

        EnvItem env = mEnvList.get(--i);
        int strId = getDlgTitle(env);
        if(strId >= 0) {
            View dlgView = LayoutInflater.from(getActivity()).inflate(R.layout.env_dialog, null);
            final EnvSetDlg set = (EnvSetDlg) dlgView.findViewById(R.id.dlg);
            initCstEnvSet(env.getId(), set);

            Dialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle(strId)
                    .setView(dlgView)
                    .setPositiveButton(R.string.env_save, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String str = set.slave();
                            if(!str.isEmpty())
                                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton(R.string.env_quit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();
        }

    }

    private void updateDataPacket() {
        mDataPacket = LoginStatus.getPacket();
        mEnvUpdate.setDataPacket(mDataPacket);
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
