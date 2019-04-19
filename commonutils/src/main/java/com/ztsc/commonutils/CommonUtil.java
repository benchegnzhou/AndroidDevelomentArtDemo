package com.ztsc.commonutils;


import android.content.Context;

import com.ztsc.commonutils.utilconfig.Config;

/**
 * 项目通用工具类同统一初始化入口
 */
public class CommonUtil {
    private Context mContext;
    private Config mConfig;

    private CommonUtil() {

    }

    public static CommonUtil getInstance() {
        return instance2.commonUtil;
    }

    private static class instance2 {
        private static CommonUtil commonUtil = new CommonUtil();
    }

    /**
     * 工具类统一初始化入口
     */
    public void init(Context context) {
        init(context, new Config());
    }

    /**
     * 工具类统一初始化入口
     * 创建一个自定义配置的logcat
     */
    public void init(Context context, Config config) {
        mContext = context;
        mConfig = config;
    }

    /**
     * 获取已经初始化
     * 如果这个类没有被初始化，应当则会报错要求用户进行初始化
     *
     * @return
     */
    public Context getApplicationContext() {
        if (mContext == null) {
            throw new NullPointerException("you should call method \"init\" first in your application, the method on CommonUtil class");
        }
        return mContext;
    }

    /**
     * 读取配置文件
     * @return
     */
    public Config getConfig() {
        return mConfig;
    }
}
