package com.clever.www.clevermobile.pdu.data.hash.slave;

import android.util.Log;

import com.clever.www.clevermobile.pdu.data.packages.devinfo.PduDevInfo;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.devinfo.PduDevAddr;
import com.clever.www.clevermobile.pdu.data.packages.devinfo.PduDevType;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduHashDevInfoSave {
    private static final int PDU_CMD_DEV_NAME = 1; // 设备名称
    private static final int PDU_CMD_DEV_MODE = 2; //设备工作模式
    private static final int PDU_CMD_DEV_TYPE = 3; //设备型号
    private static final int PDU_CMD_DEV_AREA = 1; // 机房
    private static final int PDU_CMD_DEV_GROUP = 2;
    private static final int PDU_CMD_DEV_CAB = 3; // 机柜
    private PduHashSlaveCom mCommon = new PduHashSlaveCom();


    /**
     * @brief 保存设备工作模式
     * @param type
     * @param data
     */
    private void devMode(PduDevType type, NetDataDomain data) {
        int mode = data.data.get(0); /*主从模式*/
        if(mode >= 0)
            type.ms = (byte) mode;
    }

    /**
     * @brief 设备信息保存
     * @param data
     */
    private void hashDevTypeSave(PduDevType type,NetDataDomain data) {
        int fc = data.fn[1] & 0x0f;
        switch (fc)
        {
            case PDU_CMD_DEV_NAME: // 设备工作名称
                mCommon.devStrSave(type.name, data);
                break;

            case PDU_CMD_DEV_MODE: // 设备工作模式
                devMode(type, data);
                break;

            case PDU_CMD_DEV_TYPE: // 设备型号
                mCommon.devStrSave(type.typeStr, data);
                break;

        }
    }


    /**
     * @brief PDU设备地址
     * @param addr
     * @param data
     */
    private void hashDevAddr(PduDevAddr addr, NetDataDomain data)
    {
        int fc = data.fn[1] & 0x0f;
        switch (fc)
        {
            case PDU_CMD_DEV_AREA: // 区
                mCommon.devStrSave(addr.area, data);
                break;

            case PDU_CMD_DEV_GROUP: // 组
                mCommon.devStrSave(addr.group, data);
                break;

            case PDU_CMD_DEV_CAB: //机柜
                mCommon.devStrSave(addr.cab, data);
                break;
        }
    }


    /**
     * @brief 设备信息保存
     * @param info
     * @param data
     */
    public void pduDevInfoSave(PduDevInfo info, NetDataDomain data)
    {
        int fc = data.fn[1] >> 4;
        switch (fc)
        {
            case PduHashConstants.PDU_CMD_DEVTYPE: // 设备类型
                hashDevTypeSave(info.type, data);
                break;

            case PduHashConstants.PDU_CMD_DEVADDR: // 设备地址
                hashDevAddr(info.addr, data);
                break;

            default:
                Log.d("lzy", "pdu_hashDevInfo_save err");
                break;
        }

    }

}
