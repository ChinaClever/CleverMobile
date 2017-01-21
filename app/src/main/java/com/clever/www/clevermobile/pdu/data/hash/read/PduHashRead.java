package com.clever.www.clevermobile.pdu.data.hash.read;

import com.clever.www.clevermobile.pdu.data.hash.data.PduDevHash;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashData;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashIP;
import com.clever.www.clevermobile.pdu.data.hash.data.PduHashTable;
import com.clever.www.clevermobile.pdu.data.packages.PduDataPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzy on 16-9-20.
 * Hash读取类，根据设备类型、设备IP、级联编号获取设备数据结构体
 */
public class PduHashRead {
    private PduHashData mHashData = PduHashTable.getHash();

    /**
     * 根据设备类型和设备IP  查找对象
     * @param devType 设备类型
     * @param ip 设备ip
     * @return
     */
    public PduDevHash get(int devType, String ip) {
        PduHashIP  hashIP =  mHashData.get(devType); // 根据设备代号获取对应的Hash节点
        return hashIP.get(ip); // 根据不同的设备IP 获取对应的Hash节点
    }

    /**
     * 根据设备种类、设备IP、设备号来查找设备数据
     */
    public PduDataPacket get(int devType, String ip, int num) {
        PduDevHash devHash = get(devType, ip);
        return devHash.get(num); // 根据不同的设备号 获取对应的设备Hash节点
    }

    /**
     * 根据IP 获取设备数据
     * @param ip 地址
     * @return null 错误
     */
    public PduDevHash get(String ip) {
        List<Integer> list = new ArrayList<>();
        mHashData.list(list);

        PduDevHash devHash = null;
        for(int i=0; i<list.size(); ++i) {
            int devType = list.get(i);
            PduHashIP hashIP =  mHashData.getHash(devType);
            if(hashIP != null) {
                PduDevHash hash = hashIP.getDev(ip);
                if(hash != null) {
                    devHash = hash;
                    if(hash.getMaster() != null) {
                        if(hash.getMaster().offLine > 0)
                            break;
                    }
                }
            }
        }

        return devHash;
    }

    /**
     * 获取设备副机数量
     * @param ip
     * @return 数量
     */
    public int getSlaveNum(String ip){
        PduDevHash devHash = get(ip);
        return devHash.size();
    }

    /**
     * 获取设备类型
     * @param ip
     * @return
     */
    public int getDevType(String ip){
        List<Integer> list = new ArrayList<>();
        mHashData.list(list);
        for(int i=0; i<list.size(); ++i){
            int devType = list.get(i);
            PduHashIP hashIP =  mHashData.getHash(devType);
            if(hashIP != null) {
                PduDevHash devHash = hashIP.getDev(ip);
                if(devHash != null)
                    return devType;
            }
        }
        return -1;
    }

    /**
     * 获取设备数据包
     * @param ip
     * @param num
     * @return
     */
    public PduDataPacket getPacket(String ip, int num) {
        PduDevHash devHash = get(ip);
        if(devHash != null)
            return devHash.getPacket(num);
        return null;
    }


    /**
     * 获取在线设备数据
     */
    public int getDevLineNum() {
        int num = 0;
        List<Integer> list = new ArrayList<>();
        mHashData.list(list);

        for(int i=0; i<list.size(); ++i)
        {
            PduHashIP hashIP =  mHashData.getHash(list.get(i));
            List<String> listIp = new ArrayList<>();
            hashIP.list(listIp);

            for(int j=0; j<listIp.size(); ++j)
            {
                PduDevHash hash = hashIP.get(listIp.get(j));
                List<Integer> listDev = new ArrayList<>();
                hash.list(listDev);

                for(int k=0; k<listDev.size(); ++k)
                {
                    PduDataPacket packet = hash.get(listDev.get(k));
                    if(packet.offLine > 0) {
                        num ++;
                    } else {

                    }
                }
            }
        }

        return num;
    }


}
