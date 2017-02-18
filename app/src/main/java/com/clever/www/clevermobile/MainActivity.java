package com.clever.www.clevermobile;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.clever.www.clevermobile.devShow.env.EnvFragment;
import com.clever.www.clevermobile.devShow.line.LineFragment;
import com.clever.www.clevermobile.devShow.lineList.LineListFragment;
import com.clever.www.clevermobile.devShow.loop.LoopFragment;
import com.clever.www.clevermobile.devShow.output.OutputFragment;
import com.clever.www.clevermobile.devShow.set.SetDevFragment;
import com.clever.www.clevermobile.net.udp.recv.UdpSockList;
import com.clever.www.clevermobile.pdu.dev.PduDevSpied;

public class MainActivity extends AppCompatActivity {
    private PduDevSpied mDevSpied = PduDevSpied.get();
    private UdpSockList mUdpSocket = new UdpSockList();
    private LineFragment mLine = null;
    private LineListFragment mLineList = null;
    private LoopFragment mLoop = null;
    private EnvFragment mEnv = null;
    private OutputFragment mOutput = null;
    private SetDevFragment mSetDev = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNet();
        initBtmenu();
    }

    private void initNet() {
//        mDevSpied.get().startThread();
//        UdpSend udp = new UdpSend();
//        String str = "luozhiyong";
//        udp.dbSent(8080, str.getBytes(), str.length());
//        new UdpHeartbeat().start();
//        new UdpSockList().createSocket();

//        mUdpSocket.createSocket();
    }

    private void initBtmenu() {
        RadioGroup btmenu_bar = (RadioGroup) findViewById(R.id.bottom_bar);
        btmenu_bar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btmenuChanged(i);
            }
        });
        RadioButton lineBtn = (RadioButton) findViewById(R.id.rb_line);
        lineBtn.setChecked(true);
    }


    private void btmenuChanged(int id) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);

        switch (id) {
            case R.id.rb_line:
//                if(mLineList == null){
//                    mLineList = new LineListFragment();
//                    transaction.add(R.id.content,mLineList);
//                }
//                transaction.show(mLineList);

                if(mLine == null){
                    mLine = new LineFragment();
                    transaction.add(R.id.content,mLine);
                }
                transaction.show(mLine);

//                if(mLoop == null){
//                    mLoop = new LoopFragment();
//                    transaction.add(R.id.content,mLoop);
//                }
//                transaction.show(mLoop);

                break;

            case R.id.rb_output:
                if(mOutput == null) {
                    mOutput = new OutputFragment();
                    transaction.add(R.id.content,mOutput);
                }
                transaction.show(mOutput);
                break;

            case R.id.rb_env:
                if(mEnv == null){
                    mEnv = new EnvFragment();
                    transaction.add(R.id.content,mEnv);
                }
                transaction.show(mEnv);
                break;

            case R.id.rb_setting:
               if(mSetDev == null) {
                   mSetDev = new SetDevFragment();
                   transaction.add(R.id.content,mSetDev);
               }
                transaction.show(mSetDev);
                break;
        }

        transaction.commit();
    }


    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(mLine != null) fragmentTransaction.hide(mLine);
        if(mLineList != null)fragmentTransaction.hide(mLineList);
        if(mLoop != null)fragmentTransaction.hide(mLoop);
        if(mOutput != null)fragmentTransaction.hide(mOutput);
        if(mEnv != null)fragmentTransaction.hide(mEnv);
        if(mSetDev != null)fragmentTransaction.hide(mSetDev);
    }


}
