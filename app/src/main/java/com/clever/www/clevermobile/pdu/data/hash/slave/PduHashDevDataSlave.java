package com.clever.www.clevermobile.pdu.data.hash.slave;

import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.base.PduDataBase;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDataUnit;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduDevData;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduEnvData;
import com.clever.www.clevermobile.pdu.data.packages.devdata.PduObjData;

/**
 * Created by lzy on 16-9-18.
 */
public class PduHashDevDataSlave {
    private static final int PDU_CMD_VALUE =1;
    private static final int PDU_CMD_MIN = 2; //最小值
    private static final int PDU_CMD_MAX = 3; //最大值
    private static final int PDU_CMD_ALARM = 4; //报警
    private static final int PDU_CDM_CRMIN = 5;//临界最小值
    private static final int PDU_CMD_CRMAX = 6; //临界最大值
    private static final int PDU_CMD_CRALARM = 7; //临界报警

    private static final int PDU_CMD_CUR = 1; //电流
    private static final int PDU_CMD_VOL = 2; // 电压
    private static final int PDU_CMD_POW = 3; // 功率
    private static final int PDU_CMD_ELE = 4; // 电能
    private static final int PDU_CMD_PF = 5; // 功率因素
    private static final int PDU_CMD_SW = 6; // 开关状态 0 表示未启用
    private static final int PDU_CMD_CA = 7; // 排碳量
    private static final int PDU_CMD_RATE = 8;//电压频率

    private PduHashSlaveCom mCommon = new PduHashSlaveCom();

    // 数据单元处理 主要针对 当前值、最大、小值等数据的处理
    private void unitData(PduDataUnit unit, NetDataDomain data)  {
        PduDataBase ptr = null;
        int sizeBit = 2;

        int fc = data.fn[1] & 0x0f; // 处理功能码，第二字节的低四位数据
        switch (fc) {
            case PDU_CMD_VALUE:
                ptr = unit.value;
                break;

            case PDU_CMD_MIN:
                ptr = unit.min;
                break;

            case PDU_CMD_MAX:
                ptr = unit.max;
                break;

            case PDU_CMD_ALARM:
                sizeBit = 1;
                ptr = unit.alarm;
                break;

            case PDU_CDM_CRMIN:
                ptr = unit.crMin;
                break;

            case PDU_CMD_CRMAX:
                ptr = unit.crMax;
                break;

            case PDU_CMD_CRALARM:
                sizeBit = 1;
                ptr = unit.crAlarm;
                break;

            default:
                break;
        }

        if(ptr != null)
            mCommon.saveHashIntData(ptr, data.len, data.data, sizeBit);
    }

    // 设备对象数据的处理 主要电流、电压、功率、电能
    private void objData(PduObjData obj, NetDataDomain data){
        PduDataBase ptr = null;
        int sizeBit = 2;

        int fc = data.fn[1] >> 4; // // 处理功能码，第二字节的高四位
        switch (fc){
            case PDU_CMD_CUR: // 电流
                unitData(obj.cur, data);
                break;

            case PDU_CMD_VOL: // 电压
                unitData(obj.vol, data);
                break;

            case PDU_CMD_POW: // 功率
                sizeBit = 4;
                ptr = obj.pow;
                break;

            case PDU_CMD_ELE: // 电能
                sizeBit = 4;
                ptr = obj.ele;
                break;

            case PDU_CMD_PF: // 功率因素
                ptr = obj.pf;
                break;

            case PDU_CMD_SW: // 开关状态
                sizeBit = 1;
                ptr = obj.sw;
                break;

            case PDU_CMD_CA: // 排碳量
                ptr = obj.carbon;
                break;

            case PDU_CMD_RATE: //电压频率
                ptr = obj.rate;
                break;

            default:
                break;
        }

        if(ptr != null)
            mCommon.saveHashIntData(ptr, data.len, data.data, sizeBit);
    }

    // 环境数据的处理
    private void envData(PduEnvData env, NetDataDomain data)  {
        PduDataBase ptr = null;
        int sizeBit = 1;

        int fc = data.fn[1] >> 4; // // 处理功能码，第二字节的高四位
        switch (fc) {
            case PduHashConstants.PDU_CMD_TEMP: // 温度
                unitData(env.tem, data);
                break;

            case PduHashConstants.PDU_CMD_HUM: //湿度
                unitData(env.hum, data);
                break;

            case PduHashConstants.PDU_CMD_DOOR: //门禁
                ptr = env.door;
                break;

            case PduHashConstants.PDU_CMD_WATER: //水浸
                ptr = env.water;
                break;

            case PduHashConstants.PDU_CMD_SMOKE: //烟雾
                ptr = env.smoke;
                break;

            default:
                break;
        }
        if(ptr != null)
            mCommon.saveHashIntData(ptr, data.len, data.data, sizeBit);
    }

    public void hashDevDataSlave(PduDevData dev, NetDataDomain data){
        int fc = data.fn[0]; //根据功能码，进行分支处理
        switch (fc) {
            case PduHashConstants.PDU_CMD_LINE: //设备相参数
                objData(dev.line, data);
                break;

            case PduHashConstants.PDU_CMD_LOOP: //设备回路参数
                objData(dev.loop, data);
                break;

            case PduHashConstants.PDU_CMD_OUTPUT: // 设备输出位
                objData(dev.output, data);
                break;

            case PduHashConstants.PDU_CMD_ENV: // 环境数据
                envData(dev.env, data);
                break;
        }
    }
}
