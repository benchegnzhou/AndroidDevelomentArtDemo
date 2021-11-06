package com.zbc.androiddevelomentartdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class DemoTestActivity extends AppCompatActivity {


    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_serializable)
    TextView tvSerializable;
    @BindView(R.id.tv_unserializable)
    TextView tvUnserializable;
    @BindView(R.id.tv_parcelable)
    TextView tvParcelable;
    @BindView(R.id.tv_unparcelable)
    TextView tvUnparcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_test);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_serializable, R.id.tv_unserializable, R.id.tv_parcelable, R.id.tv_unparcelable})
    public void onclick(View view) {
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
                break;
            default:
        }
    }
}
