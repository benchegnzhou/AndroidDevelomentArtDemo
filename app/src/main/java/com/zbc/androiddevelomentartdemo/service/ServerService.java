package com.zbc.androiddevelomentartdemo.service;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import androidx.annotation.Nullable;

import com.zbc.androiddevelomentartdemo.content.ContentValue;
import com.zbc.androiddevelomentartdemo.entity.SystemMsgBean;
import com.ztsc.commonutils.logcat.LogUtil;

/**
 * Created by benchengzhou on 2019/4/13  14:12 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 服务service
 * 类    名： ServerService
 * 备    注：
 */

public class ServerService extends Service {

    private final Messenger mMessager = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            LogUtil.e("ServerService's handleMessage is invoke");
            switch (msg.what) {
                case ContentValue.MSG_FROM_CLIENT:
                    Bundle data = msg.getData();
                    //如果本次通讯是跨进程的数据传递，一定要设置classLoader，否则会因找不到可用的classLoader而报错 Parcel: Class not found when unmarshalling: com.zbc.androiddevelomentartdemo.entity.SystemMsgBean
                    data.setClassLoader(getClass().getClassLoader());
                    LogUtil.e("----------------MessengerHandler-----------------");
                    LogUtil.e("log from ServerService:\n the pracelable is :"
                            + data.getParcelable("systemMsgBean") + "\n the String msg is : "
                            + data.getString("msg") + "\n the int code is : "
                            + data.getInt("code"));

                    Messenger messenger = msg.replyTo;
                    Message message = Message.obtain(null, ContentValue.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "收到了，别比比，劳资烦");
                    message.setData(bundle);
                    try {
                        messenger.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                default:
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.e("ServerService binded success!");
        Bundle bundle = intent.getExtras();
        SystemMsgBean systemMsgBean = bundle.getParcelable("systemMsgBean");
        LogUtil.e("onBind 接收到的消息为————————\n" + systemMsgBean.toString());
        return mMessager.getBinder();
    }
}
