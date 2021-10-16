package com.zbc.androiddevelomentartdemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.custom_round_view.constant.MOUDLE_AROUTER;
import com.zbc.androiddevelomentartdemo.activity.BadgeDemoActivity;
import com.zbc.androiddevelomentartdemo.activity.CustomViewActivity;
import com.zbc.androiddevelomentartdemo.activity.DemoAnimationActivity;
import com.zbc.androiddevelomentartdemo.activity.DemoTestActivity;
import com.zbc.androiddevelomentartdemo.activity.DrawableTestActivity;
import com.zbc.androiddevelomentartdemo.activity.GuestureDelectorActivity;
import com.zbc.androiddevelomentartdemo.activity.IBinderTestActivity;
import com.zbc.androiddevelomentartdemo.activity.RemoteViewDemoActivity;
import com.zbc.androiddevelomentartdemo.activity.ScrolllConflictActivity;
import com.zbc.androiddevelomentartdemo.content.ContentValue;
import com.zbc.androiddevelomentartdemo.entity.SystemMsgBean;
import com.zbc.androiddevelomentartdemo.entity.UserBean;
import com.zbc.androiddevelomentartdemo.service.ServerService;
import com.ztsc.commonutils.logcat.LogUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benchengzhou on 2019/4/19  11:31 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： MainActivity
 * 备    注：
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_next3)
    TextView tvNext3;
    @BindView(R.id.tv_send_msg)
    TextView tvSendMsg;
    @BindView(R.id.tv_gesturedetector)
    TextView tvGesturedetector;
    @BindView(R.id.tv_scroll_conflict)
    TextView tvScrollConflict;
    private Messenger mService;
    private Messenger mGetReplyMessenger = new Messenger(new MessageHandle());
    private final int RC_LOCATION_CONTACTS_PERM = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestPermissions();
    }


    @OnClick({R.id.tv_next3, R.id.tv_send_msg, R.id.tv_remoteview
            , R.id.tv_gesturedetector, R.id.tv_scroll_conflict, R.id.tv_ibinder_test
            , R.id.tv_badge_demo, R.id.tv_drawable_test, R.id.tv_android_animation
            , R.id.tv_android_custom_view, R.id.tv_custom_view_advanced})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_next3:
                startActivity(new Intent(this, DemoTestActivity.class));
                break;
            case R.id.tv_send_msg:
                if (mService != null) {
                    Message message = Message.obtain(null, ContentValue.MSG_FROM_CLIENT);
                    Bundle data = new Bundle();

                    data.putString("msg", "hello！this is a client.I there are some message i want say you");
                    data.putInt("code", 100);
                    data.putParcelable("systemMsgBean", new SystemMsgBean("a00001"
                            , "Hello ! 我是通过bundle传递数据"
                            , System.currentTimeMillis()
                            , 0
                    ));
                    message.setData(data);


                    message.replyTo = mGetReplyMessenger;
                    try {
                        mService.send(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, ServerService.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("systemMsgBean", new SystemMsgBean("a00001"
                            , "Hello ! 我是通过bundle传递数据"
                            , System.currentTimeMillis()
                            , 0
                    ));
                    intent.putExtras(bundle);
                    bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                }
                break;

            case R.id.tv_ibinder_test:


                Debug.startMethodTracing("tv_ibinder_test");
                ArrayList<String> stringList = new ArrayList<>();
                stringList.add("a");
                stringList.add("b");
                stringList.add("c");


                Intent intent = new Intent(MainActivity.this, IBinderTestActivity.class);
                intent.putExtra("intValue", 12);
                intent.putExtra("StringValue", "this is String value");
                intent.putStringArrayListExtra("StringListValue", stringList);
                intent.putExtra("SerializableValue"
                        , new UserBean("小明", 2, "北京"
                                , new UserBean.HomeTown("北京北", "1263.15", "1545.24")));

                intent.putExtra("ParcelableValue"
                        , new SystemMsgBean("id_123", "推送消息", System.currentTimeMillis(), 0));

                Bundle bundle = new Bundle();
                bundle.putInt("intValue", 12);
                bundle.putString("StringValue", "this is String value");
                bundle.putStringArrayList("StringListValue", stringList);

                bundle.putSerializable("SerializableValue", new UserBean("小明", 2, "北京"
                        , new UserBean.HomeTown("北京北", "1263.15", "1545.24")));

                bundle.putParcelable("ParcelableValue"
                        , new SystemMsgBean("id_123", "推送消息", System.currentTimeMillis(), 0));

                intent.putExtras(bundle);

                startActivity(intent);
                Debug.stopMethodTracing();
                break;

            case R.id.tv_gesturedetector:
                Intent intentg = new Intent(MainActivity.this, GuestureDelectorActivity.class);
                //手势识别测试
                startActivity(intentg);
                break;
            case R.id.tv_scroll_conflict:
                startActivity(new Intent(MainActivity.this, ScrolllConflictActivity.class));
                break;

            case R.id.tv_remoteview:
                startActivity(new Intent(this, RemoteViewDemoActivity.class));
                break;
            case R.id.tv_badge_demo:
                startActivity(new Intent(this, BadgeDemoActivity.class));
                break;
            case R.id.tv_drawable_test:
                startActivity(new Intent(this, DrawableTestActivity.class));
                break;
            case R.id.tv_android_animation:
                startActivity(new Intent(this, DemoAnimationActivity.class));
                break;
            case R.id.tv_android_custom_view:
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            case R.id.tv_custom_view_advanced:
                ARouter.getInstance().build(MOUDLE_AROUTER.ROUNDVIEW_MAIN_ACTIVITY).navigation();
                break;
            default:
        }
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.e("the ComponentName is :" + name.getClassName());
            mService = new Messenger(service);

            Message message = Message.obtain(null, ContentValue.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "hello！this is a client.");
            data.setClassLoader(SystemMsgBean.class.getClassLoader());

            data.putParcelable("systemMsgBean", new SystemMsgBean("a00001"
                    , "俺是Message传递过来的数据"
                    , System.currentTimeMillis()
                    , 0
            ));
            message.setData(data);

            message.replyTo = mGetReplyMessenger;
            try {
                mService.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onBindingDied(ComponentName name) {

        }
    };


    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }


    private class MessageHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ContentValue.MSG_FROM_SERVICE:
                    LogUtil.e(msg.getData().getString("reply"));
                    break;
                default:
            }
        }
    }


    private void requestPermissions() {
        //acitivty中申请权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_LOCATION_CONTACTS_PERM);
    }


    //activity权限授权结果回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
