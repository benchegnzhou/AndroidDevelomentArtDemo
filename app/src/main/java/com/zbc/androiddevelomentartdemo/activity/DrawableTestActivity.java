package com.zbc.androiddevelomentartdemo.activity;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.zbc.androiddevelomentartdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by benchengzhou on 2019/5/20  9:56 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： drawable在android 中的使用
 * 类    名： DrawableTestActivity
 * 备    注：
 */
public class DrawableTestActivity extends AppCompatActivity {

    @Bind(R.id.tv_drawable_transition)
    TextView tvDrawableTransition;
    @Bind(R.id.tv_drawable_layerdrawable)
    TextView tvDrawableLayerdrawable;
    @Bind(R.id.tv_drawable_level)
    TextView tvDrawableLevel;
    @Bind(R.id.tv_drawable_insert)
    TextView tvDrawableInsert;
    @Bind(R.id.tv_drawable_clip)
    TextView tvDrawableClip;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    private TransitionDrawable mDrawable;
    private LevelListDrawable mLevelBackground;
    private int currentlevel = 0;
    private ClipDrawable mClipBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_test);
        ButterKnife.bind(this);
        mDrawable = (TransitionDrawable) tvDrawableTransition.getBackground();
        mDrawable.startTransition(1000);
        tvDrawableLevel.setTextColor(getResources().getColor(R.color.color_text_666666));
        mLevelBackground = (LevelListDrawable) tvDrawableLevel.getBackground();
        mClipBackground = (ClipDrawable) tvDrawableClip.getBackground();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mClipBackground.setLevel(progress*10000/100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @OnClick({R.id.tv_drawable_transition, R.id.tv_drawable_level})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_drawable_transition:
                mDrawable.reverseTransition(1000);
                break;
            case R.id.tv_drawable_level:
                tvDrawableLevel.setTextColor(getResources().getColor(R.color.colorwhite));
                currentlevel = currentlevel > 5 ? 1 : 6;
                mLevelBackground.setLevel(currentlevel);
                break;
            default:

        }
    }
}
