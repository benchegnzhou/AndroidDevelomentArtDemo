package com.zbc.androiddevelomentartdemo.application;

import android.app.Application;
import android.content.Context;

import com.ztsc.commonutils.CommonUtil;
import com.ztsc.commonutils.utilconfig.Config;

/**
 * Created by benchengzhou on 2019/4/19  11:00 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： MApplication
 * 备    注：
 */

public class MApplication extends Application {

    public static Application sApplication;
    public static Context sAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        //获取全局上下文
        sApplication = this;
        sAppContext = this.getApplicationContext();


        CommonUtil.getInstance().init(this.getApplicationContext(), new Config()
                .setLogOpen(true)
                .setLogTag("ZBC_DEVELOPMENT_ART_DEMO")
                .setToastOpen(true));

    }

}
