package com.zbc.androiddevelomentartdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zbc.androiddevelomentartdemo.R;
import com.zbc.androiddevelomentartdemo.entity.UserBean;
import com.zbc.androiddevelomentartdemo.util.FileUtils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benchengzhou on 2019/4/12  11:15 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 序列化工具测试
 * 类    名： DemoTestActivity
 * 备    注：
 */

public class DemoTestActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvMsg;
    TextView tvSerializable;
    TextView tvUnserializable;
    TextView tvParcelable;
    TextView tvUnparcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_test);

        tvMsg = findViewById(R.id.tv_msg);
        tvSerializable = findViewById(R.id.tv_serializable);
        tvUnserializable = findViewById(R.id.tv_unserializable);
        tvParcelable = findViewById(R.id.tv_parcelable);
        tvUnparcelable = findViewById(R.id.tv_unparcelable);
        tvMsg.setOnClickListener(this);
        tvSerializable.setOnClickListener(this);
        tvParcelable.setOnClickListener(this);
        tvUnparcelable.setOnClickListener(this);

        // 内存泄露测试
        thread.start();
        handler= new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                while (true){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("-----","Handler is active");
                    Log.e("-----",msg.getTarget().toString());
                }
            }
        };
    }
    HandlerThread thread = new HandlerThread("test-Thread");
    Handler handler ;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_serializable:
                try {
                    UserBean userBean = new UserBean("婉君", 24, "淄川", new UserBean.HomeTown("淄博·淄川", "35.154145", "114.155"));
                    ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FileUtils.getPrimaryDirPath() + "/" +
                            "userMsg.text"));
                    outputStream.writeObject(userBean);
                    outputStream.close();
                    tvMsg.setText("数据写入成功\n" + userBean.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tv_unserializable:
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FileUtils.getPrimaryDirPath() + "/" +
                            "userMsg.text"));
                    UserBean userBean2 = (UserBean) inputStream.readObject();
                    tvMsg.setText("数据读取成功\n" + userBean2.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.tv_parcelable:
//                BindViewer BindViewer = new BindViewer();
                break;
            case R.id.tv_unparcelable:
                handler.sendMessage(new Message());
                break;
            default:
        }
    }
}
