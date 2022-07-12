package com.zbc.androiddevelomentartdemo.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zbc.androiddevelomentartdemo.R;
import com.zbc.androiddevelomentartdemo.content.ActRouter;
import com.zbc.androiddevelomentartdemo.view.CustomTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by benchengzhou on 2019/6/16  15:22 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： android自定义控件基础
 * 类    名： CustomViewActivity
 * 备    注：
 */

@Route(path = ActRouter.CUSTOM_VIEW_ACTIVITY)
public class CustomViewActivity extends AppCompatActivity {

    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_android_custom_view)
    TextView tvAndroidCustomView;
    @BindView(R.id.tv_custom)
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
