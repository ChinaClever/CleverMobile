package com.clever.www.clevermobile.login;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.net.tcp.client.TcpLogin;
import com.clever.www.clevermobile.common.timer.HanderTimer;

/**
 * Author: lzy. Created on: 16-11-4.
 */
public class LoginConnectWait extends LinearLayout{
    private static final int TIME_OUT = 20;
    private TcpLogin login = new TcpLogin();
    private ProgressBar progressBar=null;
    private Timers timer = new Timers();
    private Context context=null;
    private Dialog dialog=null;
    private Button connectBtn, disBtn;
    private int count = -1;

    public LoginConnectWait(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_connect_wait, this);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        timer.start(500);
        login.connectSer(LoginStatus.login_ip, LoginStatus.login_usr, LoginStatus.login_pwd, LoginStatus.login_devNum);
    }

    public void setView(Context context, Dialog dialog, Button conBtn, Button disBtn) {
        this.context = context;
        this.dialog = dialog;
        this.connectBtn = conBtn;
        this.disBtn = disBtn;
    }

    private void loginOver(boolean b) {
        timer.stop();
        dialog.dismiss();

        if(b) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(context, R.string.login_OK, Toast.LENGTH_LONG).show();
            connectBtn.setEnabled(false);
            disBtn.setEnabled(true);
        } else {
            Toast.makeText(context, R.string.login_err, Toast.LENGTH_LONG).show();
            connectBtn.setEnabled(true);
            disBtn.setEnabled(false);
        }
        LoginStatus.isLogin = b;
    }

    private void updateData() {
        boolean ret = login.isLogin;
        if(ret) {/* 登陆 功*/
            loginOver(ret);
        } else {
            if (count++ >= TIME_OUT)
                loginOver(ret);
        }
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            updateData();
        }
    }
}
