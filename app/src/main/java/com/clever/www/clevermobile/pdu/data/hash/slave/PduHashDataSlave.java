package com.clever.www.clevermobile.pdu.data.hash.slave;

import com.clever.www.clevermobile.net.data.datadone.NetConstants;
import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.net.data.packages.NetDataPacket;
import com.clever.www.clevermobile.pdu.data.hash.data.PduDevHash;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashData;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashIP;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashTable;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

/**
 * Created by lzy on 16-9-18.
 * 数据保存
 */
public class PduHashDataSlave {
    private PduHashSlaveCom mCommon = new PduHashSlaveCom();
    private PduHashData mHashData = PduHashTable.getHash();
    private PduHashDevDataSlave mDevDataSlave = new PduHashDevDataSlave();
    private PduHashDevInfoSave mDevInfo = new PduHashDevInfoSave();
    private PduHashDevNetSave mDevNet = new PduHashDevNetSave();
    private PduHashOutputSave mOutput = new PduHashOutputSave();
    private PduHashDevUsrSave mDevUsr = new PduHashDevUsrSave();

    private void hashDataFunction(PduDataPacket dev, NetDataDomain data) {
        int fc = data.fn[0]; //根据功能码，进行分支处理
        switch (fc) {
            case PduHashConstants.PDU_CMD_STATUS: // 设备工作状态
                dev.state = data.data.get(0);
                break;

            case PduHashConstants.PDU_CMD_LOOP: //回路参数
            case PduHashConstants.PDU_CMD_LINE: //设备相参数
            case PduHashConstants.PDU_CMD_OUTPUT: // 设备输出位
            case PduHashConstants.PDU_CMD_ENV: // 环境数据
                mDevDataSlave.hashDevDataSlave(dev.data, data);
                break;

            case PduHashConstants.PDU_CMD_DEVINFO: // 设备信息
                mDevInfo.pduDevInfoSave(dev.info, data);
                break;

            case PduHashConstants.PDU_CMD_DEVUSR: // 设备用户信息
                mDevUsr.hashDevUsrSave(dev.usr, data);
                break;

            case PduHashConstants.PDU_CMD_DEVNET: // 设备网络信息
                mDevNet.devNetSave(dev.net,data);
                break;

            case PduHashConstants.PDU_CMD_OUTPUT_NAME: // 输出位名称
                mOutput.outputName(dev.output.name, data);
                break;
        }
    }

    /**
     * Hash数据保存的入口函数
     * 主要是针对代号段的处理，pdu_dev_code
     *      对数据进行网络传输类型、传输方向、及版本的验证;
     *      根据IP、代号段中的设备类型、设备号来查找对应的设备数据节点
     * @param ip 设备IP
     * @param dataPacket 数据包
     */
    public void slave(String ip, NetDataPacket dataPacket) {
        boolean ret = mCommon.checkTranType(dataPacket.code.type, dataPacket.code.trans); // 网络传输类型、传输方向验证
        if(ret) {
            int devType = mCommon.getDevCode(dataPacket.code.devCode);  // 获取设备类型码
            if(devType > 0) {
                PduHashIP hashIP = mHashData.get(devType);
                PduDevHash devHash = hashIP.get(ip);
                PduDataPacket dev = devHash.get(dataPacket.dataDomain.addr);

                if(dataPacket.code.version == NetConstants.DATA_DEV_VERSION) { // 版本号的验证
                    dev.devType = devType; // 设备型号
                    dev.devNum = dataPacket.dataDomain.addr; // 设备地址
                    dev.ip.set(ip); // 设备IP
                    dev.offLine = NetConstants.PDU_OFF_LINE_TIME; // 设备在线标识

                    if(devHash.isNew) { // 是新设备

                    }
                    hashDataFunction(dev, dataPacket.dataDomain);
                }
            }
        }
    }

}
