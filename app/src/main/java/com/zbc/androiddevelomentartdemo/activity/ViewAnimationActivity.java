package com.zbc.androiddevelomentartdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.zbc.androiddevelomentartdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewAnimationActivity extends AppCompatActivity {

    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.btn_translation)
    Button btnTranslation;
    @BindView(R.id.btn_rotate)
    Button btnRotate;
    @BindView(R.id.btn_scale)
    Button btnScale;
    @BindView(R.id.btn_alpha)
    Button btnAlpha;
    @BindView(R.id.btn_translation_xml)
    Button btnTranslationXml;
    @BindView(R.id.btn_rotate_xml)
    Button btnRotateXml;
    @BindView(R.id.btn_scale_xml)
    Button btnScaleXml;
    @BindView(R.id.btn_alpha_xml)
    Button btnAlphaXml;
    private int mImgHeight;
    private int mImgWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);
        ButterKnife.bind(this);


        ViewTreeObserver observer = ivImg.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {


            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                ivImg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mImgWidth = ivImg.getMeasuredWidth();
                mImgHeight = ivImg.getMeasuredHeight();
            }
        });

    }


    @OnClick({R.id.btn_translation
            , R.id.btn_rotate
            , R.id.btn_scale
            , R.id.btn_alpha
            , R.id.btn_translation_xml
            , R.id.btn_scale_xml
            , R.id.btn_rotate_xml
            , R.id.btn_alpha_xml})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_translation:
                int left = ivImg.getLeft() - 500;
                int top = ivImg.getTop() - 500;
                TranslateAnimation translateAnimation = new TranslateAnimation(left, left + 500, top, top + 500);
                translateAnimation.setDuration(1200);
                translateAnimation.setInterpolator(new BounceInterpolator());
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                ivImg.startAnimation(translateAnimation);
                break;
            case R.id.btn_rotate:
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 0, 0);
                rotateAnimation.setDuration(1200);
                rotateAnimation.setInterpolator(new BounceInterpolator());
                ivImg.startAnimation(rotateAnimation);
                break;
            case R.id.btn_scale:
                ScaleAnimation scaleAnimation = new ScaleAnimation(0.1f,1.0f,2.0f,1.0f  );
                scaleAnimation.setDuration(1200);
//                scaleAnimation.setInterpolator(new BounceInterpolator());
                ivImg.startAnimation(scaleAnimation);
                break;
            case R.id.btn_alpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1);
                alphaAnimation.setDuration(1200);
                alphaAnimation.setInterpolator(new BounceInterpolator());
                ivImg.startAnimation(alphaAnimation);
                break;
            case R.id.btn_translation_xml:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
                animation.setInterpolator( new AccelerateInterpolator());
                ivImg.startAnimation(animation);
                break;
            case R.id.btn_rotate_xml:
                Animation animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
                animationRotate.setInterpolator( new AccelerateInterpolator());
                ivImg.startAnimation(animationRotate);
                break;
            case R.id.btn_scale_xml:
                Animation animationScale= AnimationUtils.loadAnimation(this, R.anim.scale_animation);
                animationScale.setInterpolator( new AccelerateInterpolator());
                ivImg.startAnimation(animationScale);
                break;
            case R.id.btn_alpha_xml:
                Animation animationAlpha= AnimationUtils.loadAnimation(this, R.anim.alpha_animation);
                animationAlpha.setInterpolator( new AccelerateInterpolator());
                ivImg.startAnimation(animationAlpha);
                break;
            default:
        }
    }


}
