package com.zbc.androiddevelomentartdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.zbc.androiddevelomentartdemo.R;
import com.zbc.androiddevelomentartdemo.view.CustomTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benchengzhou on 2019/6/16  15:22 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： android自定义控件基础
 * 类    名： CustomViewActivity
 * 备    注：
 */

public class CustomViewActivity extends AppCompatActivity {

    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.tv_android_custom_view)
    TextView tvAndroidCustomView;
    @Bind(R.id.tv_custom)
    CustomTextView tvCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
        initLisner();
        initData();
    }

    private void initLisner() {

    }

    private void initData() {
        tvMessage.setText("自定义控件基础知识介绍,一个控件自定义，先后经历view的测量、布局位置摆放、绘制");
        tvCustom.setText("自定义控件基础知识介绍,一个控件自定义，先后经历view的测量、布局位置摆放、绘制。自定义控件基础知识介绍,一个控件自定义，先后经历view的测量、布局位置摆放、绘制")
        .settextColor(0xffffffff)
        .setTextSize(72);
    }


}
