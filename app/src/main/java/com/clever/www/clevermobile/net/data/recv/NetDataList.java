package com.clever.www.clevermobile.net.data.recv;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lzy on 16-9-5.
 * 网络数据包链表   所有接收到的数据都会保存到此链表中，
 * 1、请使用get()方法获取唯一的对象
 * 2、提供常用的接口方法
 *
 */
public class NetDataList {
    private List<NetDataBase> mList = new LinkedList<>();
    private static NetDataList mDataList = null;

    /**
     * 获取唯一对象
     * @return
     */
    public static NetDataList get(){
        if(mDataList == null) {
            mDataList = new NetDataList();
        }
        return mDataList;
    }

    public void add(NetDataBase dataBase) {
        mList.add(dataBase);
    }

    /* IP地址 前面有一个/ */
    private String getIp(String ip) {
        if(ip.contains("/"))
            ip = ip.replace("/","");
        return ip;
    }

    /**
     * 把数据保存至链表中
     * @param type 传输类型
     * @param ip IP地址
     * @param data 数据内容
     * @param len 长度
     */
    public void add(NetTransType type, String ip, byte[] data, int len){
        NetDataBase dataBase = new NetDataBase();
        dataBase.ip.append(getIp(ip));
        dataBase.type = type;
        dataBase.addData(data, len);
        add(dataBase);
    }

    /**
     * 增加TCP 数据
     * @param ip
     * @param data
     * @param len
     */
    public void addTcp(String ip, byte[] data, int len) {
        add(NetTransType.TCP,ip, data, len);
    }

    /**
     * 增加UDP 数据
     * @param ip
     * @param data
     * @param len
     */
    public void addUdp(String ip, byte[] data, int len) {
        add(NetTransType.UDP,ip, data, len);
    }

    /**
     * 获取大小
     * @return
     */
    public int size() {
        return  mList.size(); // 数据的大小
    }

    /**
     * 获取一个首元素，取出时会自动删除
     * @return 没有元素时返回空
     */
    public NetDataBase getData(){
        NetDataBase dataBase = null;
        if(size() > 0) {
            dataBase = mList.get(0);
            mList.remove(0);
        }

        return dataBase;
    }

}
