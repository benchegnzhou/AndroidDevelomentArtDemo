package com.zbc.androiddevelomentartdemo.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zbc.androiddevelomentartdemo.R;

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

    @Bind(R.id.btn_notifiction_test)
    Button btnNotifictionTest;
    @Bind(R.id.btn_appweiget_test)
    Button btnAppweigetTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_view_demo);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_notifiction_test, R.id.btn_appweiget_test})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_notifiction_test:
                Notification notification = new Notification();
                notification.icon = R.mipmap.image_a1;
                notification.tickerText = "来自测试者的测试通知";
                notification.when = System.currentTimeMillis();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Intent intent   = new Intent(this,RemoteViewDemoActivity.class);
                PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationManager  manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0,notification);
                break;
            case R.id.btn_appweiget_test:

                break;
            default:
        }
    }

}
