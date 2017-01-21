package com.clever.www.clevermobile.pdu.data.hash.slave;

import com.clever.www.clevermobile.net.data.packages.NetDataDomain;
import com.clever.www.clevermobile.pdu.data.packages.output.PduOutputName;

/**
 * Author: lzy. Created on: 16-11-8.
 */

public class PduHashOutputSave {
    private PduHashSlaveCom mCommon = new PduHashSlaveCom();

    /**
     * @brief 设置输出位的名称
     * @param name
     * @param data
     */
    public void outputName(PduOutputName name, NetDataDomain data) {
        int output = data.fn[1];
        if((output >= 0) && (output < 64))  {  // 输出位最大32位
            String str = mCommon.charToString(data.data, data.len);
            name.set(output, str); // 设置输出位的名称
        }
    }
}
