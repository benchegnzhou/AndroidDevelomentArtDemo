package com.zbc.androiddevelomentartdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.zbc.androiddevelomentartdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewAnimationActivity extends AppCompatActivity {

    @Bind(R.id.iv_img)
    ImageView ivImg;
    @Bind(R.id.btn_translation)
    Button btnTranslation;
    @Bind(R.id.btn_rotate)
    Button btnRotate;
    @Bind(R.id.btn_scale)
    Button btnScale;
    @Bind(R.id.btn_alpha)
    Button btnAlpha;
    private int mWidth;
    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        ButterKnife.bind(this);


        final View view = ivImg;
        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ivImg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mWidth = ivImg.getMeasuredWidth();
                mHeight = ivImg.getMeasuredHeight();
            }
        });

    }






    @OnClick({R.id.btn_translation, R.id.btn_rotate, R.id.btn_scale, R.id.btn_alpha})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translation:
                int left = ivImg.getLeft() - 500;
                int top = ivImg.getTop() - 500;
                TranslateAnimation translateAnimation = new TranslateAnimation(left,left+500,top,top+500);
                translateAnimation.setDuration(800);
                translateAnimation.setInterpolator(new BounceInterpolator());
                ivImg.startAnimation(translateAnimation);
                break;
            case R.id.btn_rotate:
                RotateAnimation rotateAnimation = new RotateAnimation(0,360,0,0);
                rotateAnimation.setDuration(1200);
                rotateAnimation.setInterpolator(new BounceInterpolator());
                ivImg.startAnimation(rotateAnimation);
                break;
            case R.id.btn_scale:
                ScaleAnimation scaleAnimation = new ScaleAnimation(mWidth*0.5f,mWidth,mHeight*0.5f,mWidth );
                scaleAnimation.setDuration(300);
                scaleAnimation.setInterpolator(new BounceInterpolator());
                ivImg.startAnimation(scaleAnimation);
                break;
            case R.id.btn_alpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f,1);
                alphaAnimation.setDuration(300);
                alphaAnimation.setInterpolator(new BounceInterpolator());
                ivImg.startAnimation(alphaAnimation);
                break;
            default:
        }
    }


}
