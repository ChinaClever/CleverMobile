package com.clever.www.clevermobile.titlebar;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.login.LoginActivity;
import com.clever.www.clevermobile.login.LoginStatus;

/**
 * Author: lzy. Created on: 16-11-9.
 */

public class TitleCstFragment extends Fragment{
    private Button loginBtn = null;
    private TextView titleTv=null, statusTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.title_cst_fragment, container, false);
        initView(view);
        new Timers().start(500);
        return view;
    }


    private void initView(View view) {
        titleTv = (TextView) view.findViewById(R.id.title);
        statusTv = (TextView) view.findViewById(R.id.status);

        loginBtn = (Button) view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void updateView() {
        if(LoginStatus.getLogin()) {
            titleTv.setText(LoginStatus.login_ip);
        } else {
            titleTv.setText(R.string.no_login);
        }
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            updateView();
        }
    }


}
