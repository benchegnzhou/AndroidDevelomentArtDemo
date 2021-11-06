package com.zbc.androiddevelomentartdemo.application;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
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

        initARouter();
    }

    private void initARouter() {
        // 日志开启
        ARouter.openLog();
        // 调试模式开启，如果在install run模式下运行，则必须开启调试模式
        ARouter.openDebug();
        ARouter.init(this);
    }

}
