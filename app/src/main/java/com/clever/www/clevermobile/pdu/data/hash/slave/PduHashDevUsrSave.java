package com.clever.www.clevermobile.pdu.data.hash.slave;

import android.util.Log;

import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.usr.PduGroupRights;
import com.clever.www.clevermobile.pdu.data.packages.usr.PduUsrGroup;
import com.clever.www.clevermobile.pdu.data.packages.usr.PduUsrHash;
import com.clever.www.clevermobile.pdu.data.packages.usr.PduUsrManager;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduHashDevUsrSave {
    private static final String STRING_SPLIT ="; "; //字符串分割符
    private static final int PDU_CMD_USR_NAME = 1; //用户名、密码
    private static final int PDU_CMD_USR_EMAIL = 2;  // 用户邮件
    private static final int PDU_CMD_USR_PHONE = 3; // 用户手机
    private static final int PDU_CMD_USR_GROUP = 4; // 用户组
    private static final int PDU_CMD_USR_DEL = 5; // 删除一个用户
    private static final int PDU_CMD_USR_ClEAR = 6; // 删除所有用户

    private static final int PDU_CMD_USR_INFO = 1;
    private static final int PDU_CMD_USRGROUPP = 2;
    private static final int PDU_CMD_USROUTPUT = 3;

    private PduHashSlaveCom mCommon = new PduHashSlaveCom();
    /**
     * @brief 设备用户名、密码
     * @param usrHash
     * @param data
     */
    private void usrNameSave(PduUsrHash usrHash, NetDataDomain data)  {
        String str = mCommon.charToString(data.data, data.len);
        if(str != null)  {
            String[] strlist = str.split(STRING_SPLIT);
            if(strlist.length == 2)
                usrHash.setPwd(strlist[0], strlist[1]);// 0用户名，1
            else
                Log.d("lzy", "pdu_usrName_save err: " + str);
        }
    }


    /**
     * @brief pdu_usrDel_save
     * @param usrHash
     * @param data
     */
    private void usrDel(PduUsrHash usrHash, NetDataDomain data) {
        String str = mCommon.charToString(data.data, data.len);
        if(str != null)
            usrHash.del(str);
    }


    /**
     * @brief 设置用户的邮件地址
     * @param usrHash
     * @param data
     */
    private void usrEmailSave(PduUsrHash usrHash, NetDataDomain data) {
        String str = mCommon.charToString(data.data, data.len);
        if(str != null)  {
            String[] strlist = str.split(STRING_SPLIT);
            if(strlist.length == 3) {
                String strId = strlist[1];
                int id = Integer.parseInt(strId);
                if(id >= 0)
                    usrHash.setEmil(strlist[0], id, strlist[2]);
                else
                    Log.d("lzy", "pdu_usrEmail_save err: " + str);
            }
            else
                Log.d("lzy", "pdu_usrEmail_save err: " + str);
        }
    }

    /**
     * @brief 用户手机
     * @param usrHash
     * @param data
     */
    private void usrPhoneSave(PduUsrHash usrHash, NetDataDomain data) {
        String str = mCommon.charToString(data.data, data.len);
        if(str != null)  {
            String[] strlist = str.split(STRING_SPLIT);
            if(strlist.length == 2)
                usrHash.setPhone(strlist[0],  strlist[1]);
            else
                Log.d("lzy", "pdu_usrPhone_save err: " + str);
        }
    }


    /**
     * @brief 用户所属组
     * @param usrHash
     * @param data
     */
    private void usrGroupSave(PduUsrHash usrHash, NetDataDomain data) {
        String str = mCommon.charToString(data.data, data.len);
        if(str != null)  {
            String[] strlist = str.split(STRING_SPLIT);
            if(strlist.length == 2)
                usrHash.setGroup(strlist[0],  strlist[1]);
            else
                Log.d("lzy", "pdu_usrGroup_save err: " + str);
        }
    }


    /**
     * @brief PDU设备用户信息
     * @param usrHash
     * @param data
     */
    private void hashUsrSave(PduUsrHash usrHash,NetDataDomain data) {
        int fc = data.fn[1] & 0x0f;
        switch (fc) {
            case PDU_CMD_USR_NAME: // 设备用户名、密码
                usrNameSave(usrHash, data);
                break;

            case PDU_CMD_USR_EMAIL: // 用户邮件
                usrEmailSave(usrHash, data);
                break;

            case PDU_CMD_USR_PHONE: //用户手机
                usrPhoneSave(usrHash, data);
                break;

            case PDU_CMD_USR_GROUP: //用户组
                usrGroupSave(usrHash, data);
                break;

            case PDU_CMD_USR_DEL:
                usrDel(usrHash, data);
                break;

            case PDU_CMD_USR_ClEAR:
                usrHash.del();
                break;
        }
    }



    /**
     * @brief 用户组权限设置
     * @param group
     * @param data
     */
    private void hashGroupSave(PduUsrGroup group, NetDataDomain data) {
        int fc = data.fn[1]& 0x0f;
        switch (fc) {
            // ==== 用户组权限设置，以后增加

            default:
                break;
        }
    }


    /**
     * @brief 输出位权限
     * @param data
     */
    private void hashOutputSave(PduGroupRights rights, NetDataDomain data) {
        int fc = data.fn[1]& 0x0f;
        switch (fc) {
            // ==== 输出位权限设置，以后增加


            default:
                break;
        }
    }


    /**
     * @brief 设备用户信息保存
     * @param usr
     * @param data
     */
    public void hashDevUsrSave(PduUsrManager usr,  NetDataDomain data) {
        int fc = data.fn[1] >> 4;
        switch (fc) {
            case PDU_CMD_USR_INFO: //用户信息
                hashUsrSave(usr.usrHash, data);
                break;

            case PDU_CMD_USRGROUPP:
                hashGroupSave(usr.group, data);
                break;

            case PDU_CMD_USROUTPUT:
                hashOutputSave(usr.rights, data);
                break;

            default:
                break;
        }

    }
}
