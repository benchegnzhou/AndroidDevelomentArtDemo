package com.zbc.androiddevelomentartdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zbc.androiddevelomentartdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benchengzhou on 2019/5/29  9:38 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： DemoAnimationActivity
 * 备    注：
 */

public class DemoAnimationActivity extends AppCompatActivity {

    @Bind(R.id.tv_view_animation)
    TextView tvViewAnimation;
    @Bind(R.id.tv_frame_animation)
    TextView tvFrameAnimation;
    @Bind(R.id.tv_property_animation)
    TextView tvPropertyAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_animation);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_view_animation,R.id.tv_frame_animation,R.id.tv_property_animation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_view_animation:
                startActivity(new Intent(DemoAnimationActivity.this,ViewAnimationActivity.class));
                break;
            case R.id.tv_frame_animation:
                startActivity(new Intent(DemoAnimationActivity.this,FrameAnimationActivity.class));
                break;
            case R.id.tv_property_animation:
                startActivity(new Intent(DemoAnimationActivity.this, PropertyAnimationActivity.class));
                break;
            default:
        }
    }
}
