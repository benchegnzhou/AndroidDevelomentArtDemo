package com.zbc.androiddevelomentartdemo.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.tot.badges.IconBadgeNumManager;
import com.zbc.androiddevelomentartdemo.MainActivity;
import com.zbc.androiddevelomentartdemo.R;
import com.ztsc.commonutils.toast.ToastUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benchengzhou on 2019/5/14  13:57 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： android APP桌面小图标，仿IOS
 * 类    名： BadgeDemoActivity
 * 备    注：
 */
public class BadgeDemoActivity extends AppCompatActivity {

    @BindView(R.id.tv_badge_add)
    TextView tvBadgeAdd;
    @BindView(R.id.tv_badge_sub)
    TextView tvBadgeSub;
    private IconBadgeNumManager setIconBadgeNumManager;


    private int count = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                try {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            count += 10;
                            sendIconNumNotification();
                        }
                    }).start();

                   /* NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = new NotificationCompat.Builder(BadgeDemoActivity.this, "subscribe")
                            .setContentTitle("收到一条订阅消息")
                            .setContentText("地铁沿线30万商铺抢购中！")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.iamge_a4)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.img_cat))
                            .setAutoCancel(true)
                            .setNumber(count)
                            .build();
                    Field field = notification.getClass().getDeclaredField("extraNotification");
                    Object extraNotification = field.get(notification);
                    Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
                    method.invoke(extraNotification, count);
                    manager.notify(2000, notification);*/

//                    sendToXiaoMi(String.valueOf(count));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ToastUtils.showToastShort("当前信息数" + count);
            } else if (msg.what == 200) {
                count -= 10;
                sendIconNumNotification();
                ToastUtils.showToastShort("当前信息数" + count);
            }

        }
    };


    private void sendToXiaoMi(String number) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        boolean isMiUIV6 = true;
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("您有" + number + "未读消息");
            builder.setTicker("您有" + number + "未读消息");
            builder.setAutoCancel(true);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
            notification = builder.build();
            Field e = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = e.get(notification);
            Method setMessageCount = extraNotification.getClass().getDeclaredMethod("setMessageCount", new Class[]{Integer.TYPE});
            setMessageCount.invoke(extraNotification,new Object[]{Integer.valueOf(count)});
            /*Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            field.set(miuiNotification, 20);// 设置信息数
            field = notification.getClass().getField("extraNotification");
            field.setAccessible(true);
            field.set(notification, miuiNotification);*/
            Toast.makeText(this, "Xiaomi=>isSendOk=>1", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            //miui 6之前的版本
            isMiUIV6 = false;
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name", getPackageName() + "/" + MainActivity.class);
            localIntent.putExtra("android.intent.extra.update_application_message_text", number);
            sendBroadcast(localIntent);
        } finally {
            if (notification != null && isMiUIV6) {
                //miui6以上版本需要使用通知发送
                nm.notify(101010, notification);
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge_demo);
        ButterKnife.bind(this);
        setIconBadgeNumManager = new IconBadgeNumManager();
    }

    @OnClick({R.id.tv_badge_add, R.id.tv_badge_sub})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tv_badge_add:
                handler.sendEmptyMessage(100);
                break;
            case R.id.tv_badge_sub:
                handler.sendEmptyMessage(200);
                break;
            default:
        }
    }


    private void sendIconNumNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) {
            return;
        }
        String notificationChannelId = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = createNotificationChannel();
            nm.createNotificationChannel(notificationChannel);
            notificationChannelId = notificationChannel.getId();
        }
        Notification notification = null;
        try {
            notification = new NotificationCompat.Builder(this, notificationChannelId)
                    .setSmallIcon(getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("title")
                    .setContentText("content num: " + count)
                    .setTicker("ticker")
                    .setAutoCancel(true)
                    .setNumber(count)
                    .build();
            notification = setIconBadgeNumManager.setIconBadgeNum(getApplication(), notification, count);

            nm.notify(32154, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createNotificationChannel() {
        String channelId = "test";
        NotificationChannel channel = null;
        channel = new NotificationChannel(channelId,
                "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.RED); //小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        return channel;
    }
}
