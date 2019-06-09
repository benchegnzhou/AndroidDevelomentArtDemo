package com.zbc.androiddevelomentartdemo.activity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zbc.androiddevelomentartdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benchengzhou on 2019/6/9 22:16.
 * 作者邮箱： mappstore@163.com
 * 功能描述： 帧动画测试
 * 类    名： FrameTestActivity
 * 备    注：
 */
public class FrameTestActivity extends AppCompatActivity {

    @Bind(R.id.iv_img)
    ImageView ivImg;
    @Bind(R.id.btn_start_animation)
    Button btnStartAnimation;
    private AnimationDrawable imgBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_test);
        ButterKnife.bind(this);
        ivImg.setBackgroundResource(R.drawable.frame_animation);
        imgBackground = (AnimationDrawable) ivImg.getBackground();
    }


    @OnClick({R.id.btn_start_animation})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_animation:
                imgBackground.start();
                break;
        }
    }
}
