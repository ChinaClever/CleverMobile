package com.clever.www.clevermobile.pdu.data.packages.usr;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduUsrHash {
    private Map<String, PduDevUsr> mHash = new HashMap<>();


    /**
     * @brief 获取用户信息
     * @param name
     * @return
     */
    public PduDevUsr get(String name) { return mHash.get(name); }

    /**
     * @brief 增加用户
     * @param name
     */
    private void addUsr(String name) {
        PduDevUsr usr =  get(name);
        if(usr == null)
            usr = new PduDevUsr();
        mHash.put(name, usr);
    }

    public void del() { mHash.clear(); }
    public void del(String name)  { mHash.remove(name);} // 删除用户


    /**
     * @brief 修改用户信息
     * @param usr
     * @param from
     */
    public void setUsr(PduDevUsr usr,  PduDevUsr from) {
        usr.usr.set(from.usr.get()); // 修改用户名
        usr.pwd.set(from.pwd.get()); // 修改密码
        usr.emil.set(from.emil.get());
        usr.emil2.set(from.emil2.get());
        usr.emil3.set(from.emil3.get());
        usr.phone.set(from.phone.get());
        usr.group.set(from.group.get());
    }


    public void setPwd(String name, String pwd)
    {
        addUsr(name);
        mHash.get(name).pwd.set(pwd);
    }


    /**
     * @brief 邮件设置
     * @param name
     * @param id 0 表示第一个邮件地址， 1表示第二个邮件地址
     * @param emil
     */
    public void setEmil(String name, int id, String emil)
    {
        addUsr(name);

        switch (id) {
            case 0:
                mHash.get(name).emil.set(emil);
                break;

            case 1:
                mHash.get(name).emil2.set(emil);
                break;

            case 2:
                mHash.get(name).emil3.set(emil);
                break;
            default:
                break;
        }
    }


    /**
     * @brief 设置手机号
     * @param name
     * @param phone
     */
    public void setPhone(String name, String phone)
    {
        addUsr(name);
        mHash.get(name).phone.set(phone);
    }

    /**
     * @brief 设置组名
     * @param name
     * @param group
     */
    public void setGroup(String name, String group)
    {
        addUsr(name);
        mHash.get(name).group.set(group);
    }

    /**
     * @brief 用户数量
     * @return
     */
    public int size() {
        return mHash.size();
    }


    /**
     * @brief 获取用户列表
     * @return
     */
    public int getUsrList(List<String> list) {
        Set<String> set = mHash.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String name = it.next();
            list.add(name);
        }
        return list.size();
    }

}
