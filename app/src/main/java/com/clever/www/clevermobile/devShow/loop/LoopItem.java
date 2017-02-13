package com.clever.www.clevermobile.devShow.loop;

/**
 * Author: lzy. Created on: 17-2-10.
 */

public class LoopItem {
    private int id = 0; // 回路名根据id号生成， id+1

    public LoopItem(int id) {
        this.id = id;
    }

    // 获取回路名称
    public String getName() { return "C" + (id+1); }
}
