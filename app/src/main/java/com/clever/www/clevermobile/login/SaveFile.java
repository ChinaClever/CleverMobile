package com.clever.www.clevermobile.login;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author: lzy. Created on: 16-11-3.
 */
public class SaveFile {
    private static final String FILE = "login";
    private static final String USR = "usr";
    private static final String PWD = "pwd";
    private static final String IP = "ip";
    private static final String NUM = "num";
    private static final String KEEP = "keep";

    public void saveLogin(Context context, String usr, String pwd) {
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit();
        editor.putString(USR, usr);
        editor.putString(PWD, pwd);
        editor.commit();
    }

    public String getUsr(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return pref.getString(USR, "admin");
    }

    public String getPwd(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return pref.getString(PWD, "admin");
    }

    public void saveDev(Context context, String ip, int num) {
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit();
        editor.putString(IP, ip);
        editor.putInt(NUM, num);
        editor.commit();
    }

    public String getIp(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return pref.getString(IP, "192.168.1.77");
    }

    public int getNum(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return pref.getInt(NUM, 0);
    }

    public void saveRemember(Context context, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE, Context.MODE_PRIVATE).edit();
        editor.putBoolean(KEEP, value);
        editor.commit();
    }

    public boolean getRemember(Context context) {
        SharedPreferences pref = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return pref.getBoolean(KEEP, true);
    }

    public void save(Context context) {
        saveLogin(context, LoginStatus.login_usr, LoginStatus.login_pwd);
        saveDev(context, LoginStatus.login_ip, LoginStatus.login_devNum);
        saveRemember(context, LoginStatus.isRemember);
    }

    public void read(Context context) {
        LoginStatus.isRemember = getRemember(context);
        LoginStatus.login_ip = getIp(context);
        LoginStatus.login_devNum = getNum(context);
        LoginStatus.login_usr = getUsr(context);
        LoginStatus.login_pwd = getPwd(context);
    }
}
