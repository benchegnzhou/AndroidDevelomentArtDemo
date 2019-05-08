package com.zbc.androiddevelomentartdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbc.androiddevelomentartdemo.R;
import com.zbc.androiddevelomentartdemo.entity.SystemMsgBean;
import com.zbc.androiddevelomentartdemo.entity.UserBean;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benchengzhou on 2019/4/20  10:29 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： IBinder传输数据测试
 * 类    名： IBinderTestActivity
 * 备    注：
 */

public class IBinderTestActivity extends AppCompatActivity {

    @Bind(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibinder_test);
        ButterKnife.bind(this);
        initListener();
    }


    private void initListener() {
        Intent intent = getIntent();
        int intValue = intent.getIntExtra("intValue", 0);
        String stringValue = intent.getStringExtra("StringValue");
        ArrayList<String> stringListValues = intent.getStringArrayListExtra("StringListValue");


        UserBean userBean = (UserBean) intent.getSerializableExtra("SerializableValue");

        SystemMsgBean msgBean = intent.getParcelableExtra("ParcelableValue");

        Bundle bundle = intent.getExtras();
        int intValue2 = bundle.getInt("intValue", 0);
        String stringValue2 = bundle.getString("StringValue");
        ArrayList<String> stringListValues2 = bundle.getStringArrayList("StringListValue");

        UserBean userBean2 = (UserBean) bundle.getSerializable("SerializableValue");

        SystemMsgBean msgBean2 = bundle.getParcelable("ParcelableValue");

        StringBuffer listStr = new StringBuffer();
        StringBuffer listStr2 = new StringBuffer();

        for (int i = 0; i < stringListValues.size(); i++) {
            listStr.append(stringListValues.get(i));
        }
        for (int i = 0; i < stringListValues2.size(); i++) {
            listStr2.append(stringListValues.get(i));
        }


        StringBuffer sb = new StringBuffer();
        sb.append("value in intent:\n\n")
                .append("intValue:" + intValue + "\n")
                .append("stringValue:" + stringValue + "\n")
                .append("stringListValues:" + listStr.toString() + "\n")
                .append("userBean:" + userBean.toString() + "\n")
                .append("msgBean:" + msgBean.toString() + "\n")
                .append("\n\n\nvalue in bundle:\n\n")
                .append("intValue:" +intValue2 + "\n")
                .append("stringValue:" + stringValue2 + "\n")
                .append("stringListValues:" + listStr2.toString() + "\n")
                .append("userBean:" + userBean2.toString() + "\n")
                .append("msgBean:" + msgBean2.toString() + "\n")
        ;

        tvMsg.setText(sb.toString());


        //获取到当前activity 的 view的底层容器 decorView一般就是当前界面的
        //底层容器（即setContentView所设置的View的父容器）
        ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
        ViewGroup rootView = decorView.findViewById(android.R.id.content);


        if (rootView instanceof ViewGroup) {
            ViewGroup childAt = (ViewGroup) rootView.getChildAt(0);
        }
    }

}
