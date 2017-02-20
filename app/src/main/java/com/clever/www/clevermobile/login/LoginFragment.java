package com.clever.www.clevermobile.login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.clever.www.clevermobile.R;
import com.clever.www.clevermobile.common.timer.HanderTimer;
import com.clever.www.clevermobile.net.tcp.client.TcpLogin;

/**
 * Author: lzy. Created on: 16-11-3.
 */
public class LoginFragment  extends Fragment{
    View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.login_fragment, container, false);

        initView();
        initEditText();
        initSpinner();
        initButton();
        new Timers().start(500);

        return mView;
    }

    /* EditText监听响应 */
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            Button btn = (Button) mView.findViewById(R.id.longin);
            btn.setEnabled(true);
        }
    };

    /* 初始化EditText增加监听  */
    private void initEditText() {
        EditText et = (EditText) mView.findViewById(R.id.ip);
        et.addTextChangedListener(textWatcher);

        et = (EditText) mView.findViewById(R.id.usr);
        et.addTextChangedListener(textWatcher);

        et = (EditText) mView.findViewById(R.id.pwd);
        et.addTextChangedListener(textWatcher);
    }

    private void initSpinner() {
        Spinner spinner = (Spinner) mView.findViewById(R.id.devNum);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Button btn = (Button) mView.findViewById(R.id.longin);
                btn.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 连接设备
     */
    private void connectDev(Button btn) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.login_view_dlg, null);
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.login_wait)
                .setView(view)
                .setNegativeButton(R.string.login_quit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
        LoginConnectWait wait = (LoginConnectWait) view.findViewById(R.id.wait);
        Button disBtn = (Button) mView.findViewById(R.id.disconnect);
        wait.setView(getActivity(),dialog, btn, disBtn);
        dialog.show();
    }

    /**
     * 验证ip是否合法
     *
     * @param text
     *            ip地址
     * @return 验证信息
     */
    public boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                return true;
            } else {
                Toast.makeText(getActivity(), R.string.login_ip_err, Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }


    private boolean checkInput() {
        EditText et = (EditText) mView.findViewById(R.id.ip);
        String ip =  et.getText().toString();
        if(!ip.isEmpty()) {
            if(!ipCheck(ip))
                return false;
        } else {
            Toast.makeText(getActivity(), R.string.login_ip_null, Toast.LENGTH_LONG).show();
            return false;
        }

        et = (EditText) mView.findViewById(R.id.usr);
        String str = et.getText().toString();
        if(str.isEmpty()) {
            Toast.makeText(getActivity(), R.string.login_name_null, Toast.LENGTH_LONG).show();
            return false;
        }

        et = (EditText) mView.findViewById(R.id.pwd);
        str = et.getText().toString();
        if(str.isEmpty()) {
            Toast.makeText(getActivity(), R.string.login_pwd_null, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    private void saveLoginStatus() {
        if(checkInput()) {
            EditText et = (EditText) mView.findViewById(R.id.ip);
            LoginStatus.login_ip = et.getText().toString();

            et = (EditText) mView.findViewById(R.id.usr);
            LoginStatus.login_usr = et.getText().toString();

            et = (EditText) mView.findViewById(R.id.pwd);
            LoginStatus.login_pwd = et.getText().toString();

            Spinner spinner = (Spinner) mView.findViewById(R.id.devNum);
            LoginStatus.login_devNum =  spinner.getSelectedItemPosition();

            SaveFile saveFile = new SaveFile();
            saveFile.save(getActivity());
        }
    }


    private void disConnect(Button btn) {
        TcpLogin login = new TcpLogin();
        login.disConnect();
        Toast.makeText(getActivity(), R.string.login_quited, Toast.LENGTH_LONG).show();

        btn.setEnabled(false);
        LoginStatus.isLogin = false;
        btn = (Button) mView.findViewById(R.id.longin);
        btn.setEnabled(true);
    }

    /**
     * 按钮监听类
     */
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.longin:
                    saveLoginStatus();
                    connectDev((Button) view);
                    break;

                case R.id.disconnect:
                    disConnect((Button) view);
                    break;

                case R.id.quit:
                    getActivity().finish();
                    break;
            }
        }
    }

    /**
     * 按钮初始化
     */
    private void initButton() {
        Button btn = (Button) mView.findViewById(R.id.longin);
        btn.setOnClickListener(new ButtonListener());
        if(LoginStatus.isLogin) {
            btn.setEnabled(false);
        }

        btn = (Button) mView.findViewById(R.id.disconnect);
        btn.setOnClickListener(new ButtonListener());
        btn.setEnabled(LoginStatus.isLogin);

        btn = (Button) mView.findViewById(R.id.quit);
        btn.setOnClickListener(new ButtonListener());

        initCheckBox();
    }

    /**
     * CheckBox 监听
     */
    private void initCheckBox() {
        CheckBox box = (CheckBox) mView.findViewById(R.id.checkBox);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.checkBox) {
                    LoginStatus.isRemember = b;
                    SaveFile saveFile = new SaveFile();
                    saveFile.saveRemember(getActivity(), b);
                }
            }
        });
    }

    /**
     * 初始化窗口
     */
    private void initView() {
        SaveFile saveFile = new SaveFile();
        saveFile.read(getActivity());

        EditText et = (EditText) mView.findViewById(R.id.ip);
        et.setText(LoginStatus.login_ip);

        Spinner spinner = (Spinner) mView.findViewById(R.id.devNum);
        spinner.setSelection(LoginStatus.login_devNum);

        CheckBox box = (CheckBox) mView.findViewById(R.id.checkBox);
        box.setChecked(LoginStatus.isRemember);

        if(LoginStatus.isRemember) {
            et = (EditText) mView.findViewById(R.id.usr);
            et.setText(LoginStatus.login_usr);

            et = (EditText) mView.findViewById(R.id.pwd);
            et.setText(LoginStatus.login_pwd);
        }
    }

    private void updateView() {
        Button btn = (Button) mView.findViewById(R.id.longin);
        if(LoginStatus.getLogin()) {
            btn.setEnabled(false);
        }
    }

    private class Timers extends HanderTimer {
        @Override
        public void timeout() {
            updateView();
            stop();
        }
    }

}
