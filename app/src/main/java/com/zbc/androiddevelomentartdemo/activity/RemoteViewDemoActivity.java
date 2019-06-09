package com.zbc.androiddevelomentartdemo.activity;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zbc.androiddevelomentartdemo.MainActivity;
import com.zbc.androiddevelomentartdemo.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benchengzhou on 2019/5/8  13:58 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： remoteView 测试
 * 类    名： RemoteViewDemoActivity
 * 备    注：
 */

public class RemoteViewDemoActivity extends AppCompatActivity {


    @Bind(R.id.btn_appweiget_test)
    Button btnAppweigetTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view_demo);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_notifiction_small_test, R.id.btn_notifiction_big_test, R.id.btn_appweiget_test
            , R.id.btn_androido_height, R.id.btn_androido_default})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_notifiction_small_test:
                Notification notification = new Notification();
                notification.icon = R.drawable.image_a1;
                notification.tickerText = "来自测试者的测试通知";

                notification.when = System.currentTimeMillis();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Intent intent = new Intent(this, RemoteViewDemoActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, notification);
                break;
            case R.id.btn_notifiction_big_test:
                showBigNotifitionN();


                break;
            case R.id.btn_androido_height:

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                    showNotictionN();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "chat";
                    String channelName = "聊天消息";
                    checkNotificationChannel(channelId);
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    createNotificationChannel(channelId, channelName, importance);
                    sendChatMsg();
                }


                break;
            case R.id.btn_androido_default:

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
                    showNotictionN();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                /*    String channelId = "chat";
                    String channelName = "聊天消息";
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    createNotificationChannel(channelId, channelName, importance);*/

                    String channelId = "subscribe";
                    String channelName = "订阅消息";
                    checkNotificationChannel(channelId);
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    createNotificationChannel(channelId, channelName, importance);
                    sendSubscribeMsg();
                }

                break;
            case R.id.btn_appweiget_test:
                deleteChannel("this is a tag");

                break;
            default:
        }

    }

    /**
     * 删除指定编号的渠道，删除后通知设置界面会保留删除记录
     *
     * @param channelId
     */
    private void deleteChannel(String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.deleteNotificationChannel(channelId);
        }
    }


    /**
     * 检测通知权限，在用户关闭时跳转通知权限设置，引导用户打开通知权限
     *
     * @param channelld
     */
    private void checkNotificationChannel(String channelld) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationChannel channel = manager.getNotificationChannel(channelld);
                if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                    Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                    startActivity(intent);
                    Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 版本N之前发送通知消息
     */
    private void showNotictionN() {
        Bitmap btm = BitmapFactory.decodeResource(getResources(),
                R.drawable.image_a2);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                RemoteViewDemoActivity.this).setSmallIcon(R.drawable.image_a3)
                .setContentTitle("a new message title")
                .setContentText("the context of the new message");
        //第一次提示消息的时候显示在通知栏上
        mBuilder.setTicker("New message");
        //设置桌面图标红点显示未读消息数为12，如果不需要显示红点请去掉这段代码
        mBuilder.setNumber(12);
        mBuilder.setLargeIcon(btm);
        mBuilder.setAutoCancel(true);//自己维护通知的消失

        //构建一个Intent
        Intent resultIntent = new Intent(RemoteViewDemoActivity.this, MainActivity.class);
        //封装一个Intent
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                RemoteViewDemoActivity.this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 设置通知主题的意图
        mBuilder.setContentIntent(resultPendingIntent);
        //获取通知管理器对象

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public void sendChatMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.img_cat)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.image_a2))
                .setAutoCancel(true)
                .setNumber(10)
                .build();
        manager.notify(1, notification);
    }

    public void sendSubscribeMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.image_a4)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.img_cat))
                .setAutoCancel(true)
                .build();
        manager.notify(2, notification);
    }


    /**
     * 在系统版本 android 7.0 之前上面显示大图通知消息
     */
    private void showBigNotifitionN() {
        Bitmap btm = BitmapFactory.decodeResource(getResources(),
                R.drawable.image_a2);
        Intent intent = new Intent(RemoteViewDemoActivity.this,
                MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                RemoteViewDemoActivity.this, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification noti = new NotificationCompat.Builder(
                RemoteViewDemoActivity.this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(btm)
                .setContentTitle("this is small view content title")
                .setContentText("this is small view content text")
                .setNumber(13)
                .setContentIntent(pendingIntent)
                .setStyle(
                        new NotificationCompat.InboxStyle()
                                .addLine(
                                        "M.Twain (Google+) Haiku is more than a cert...")
                                .addLine("M.Twain Reminder")
                                .addLine("M.Twain Lunch?")
                                .addLine("M.Twain Revised Specs")
                                .addLine("M.Twain ")
                                .addLine(
                                        "Google Play Celebrate 25 billion apps with Goo..")
                                .addLine(
                                        "Stack Exchange StackOverflow weekly Newsl...")
                                .setBigContentTitle("this is big notifity's title")
                                .setSummaryText("this is big notifity's SummaryText"))
                .build();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, noti);
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


    public static boolean areNotificationsEnabled(Context context) {
        NotificationManagerCompat.from(context).areNotificationsEnabled();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return isEnableV19(context);
        } else {
            return isEnableV26(context);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static boolean isEnableV19(Context context) {
        final String CHECK_OP_NO_THROW = "checkOpNoThrow";
        final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (NoSuchFieldException e) {
        } catch (InvocationTargetException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
        return false;
    }


    private static boolean isEnableV26(Context context) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        try {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
            sServiceField.setAccessible(true);
            Object sService = sServiceField.invoke(notificationManager);

            Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage"
                    , String.class, Integer.TYPE);
            method.setAccessible(true);
            return (boolean) method.invoke(sService, pkg, uid);
        } catch (Exception e) {
            return true;
        }
    }


}
