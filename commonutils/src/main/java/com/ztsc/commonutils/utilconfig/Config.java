package com.ztsc.commonutils.utilconfig;

/**
 * 工具类的通用config
 */
public class Config {
    public boolean LogOpen = true;
    public String LogTag = "ZHENG_TU_SHU_CHUANG";
    //吐司开关
    public boolean ToastOpen = true;

    /**
     * 打开日志工具类
     *
     * @param LogOpen
     * @return
     */
    public Config setLogOpen(boolean LogOpen) {
        this.LogOpen = LogOpen;
        return this;
    }

    /**
     * 设置log的tag
     *
     * @param LogTag
     * @return
     */
    public Config setLogTag(String LogTag) {
        this.LogTag = LogTag;
        return this;
    }


    /**
     * 设置toast是否开启
     * 不设置默认开启toast显示
     *
     * @param ToastOpen
     * @return
     */
    public Config setToastOpen(boolean ToastOpen) {
        this.ToastOpen = ToastOpen;
        return this;
    }
}
