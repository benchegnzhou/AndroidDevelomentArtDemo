package com.zbc.androiddevelomentartdemo.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zbc.androiddevelomentartdemo.R;
import com.zbc.androiddevelomentartdemo.view.AutoScrollView;
import com.zbc.androiddevelomentartdemo.view.CustomViewGroup;
import com.ztsc.commonutils.logcat.LogUtil;
import com.ztsc.commonutils.toast.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuestureDelectorActivity extends AppCompatActivity {


    AutoScrollView ivImg;
    @BindView(R.id.view_group_c)
    CustomViewGroup viewGroupC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guesture_delector);
        ButterKnife.bind(this);
        initListener();
    }


    private void initListener() {
        ivImg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean custom = false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.e("viewGroupC_onTouch_ACTION_DOWN");
                        custom=false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtil.e("viewGroupC_onTouch_ACTION_MOVE");
                        custom=false;
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.e("viewGroupC_onTouch_ACTION_UP");
                        custom=false;
                        break;
                    default:
                }
                return custom;
            }
        });

        ivImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogUtil.e("ivImg 我被长按了");
                return false;
            }
        });
    }


    @OnClick({R.id.iv_img})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_img:
                ivImg.scrollLeft();
//                ivImg.smoothScroll();
                ToastUtils.showToastShort("点击了");
                LogUtil.e("ivImg 我被点击了");
                break;
            default:

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ivImg.release();
    }


}
