package com.zbc.androiddevelomentartdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.ztsc.commonutils.logcat.Logger;

public class CustomViewGroup extends RelativeLayout {

    private boolean isIntercept;

    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private float startX = 0;
    private float startY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("_____________CustomViewGroup_onInterceptTouchEvent_ACTION_DOWN 被调用");
                isIntercept = false;

                break;
            case MotionEvent.ACTION_MOVE:
                Logger.e("_____________CustomViewGroup_onInterceptTouchEvent_ACTION_MOVE 被调用");
                isIntercept = false;

                break;
            case MotionEvent.ACTION_UP:
                Logger.e("_____________CustomViewGroup_onInterceptTouchEvent_ACTION_UP 被调用");
                isIntercept = false;

                break;
            default:
        }
        return /*super.onInterceptTouchEvent(ev)*/isIntercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("_____________CustomViewGroup_onTouchEvent_ACTION_DOWN 被调用");

                break;
            case MotionEvent.ACTION_MOVE:
                Logger.e("_____________CustomViewGroup_onTouchEvent_ACTION_MOVE 被调用");
                return true;
//                break;
            case MotionEvent.ACTION_UP:
                Logger.e("_____________CustomViewGroup_onTouchEvent_ACTION_UP 被调用");
                break;
            default:
        }
        return /*super.onTouchEvent(event)*/ true;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Logger.e("_____________CustomViewGroup_dispatchTouchEvent_ACTION_DOWN 被调用");
                startX = ev.getRawX();
                startY = ev.getRawY();
                return super.dispatchTouchEvent(ev);

            case MotionEvent.ACTION_MOVE:
                Logger.e("_____________CustomViewGroup_dispatchTouchEvent_ACTION_MOVE 被调用");
                float diffX = startX - ev.getRawX();
                float diffY = startY - ev.getRawY();
                isIntercept = Math.abs(diffX) > 150 || Math.abs(diffY) > 150;

                return super.dispatchTouchEvent(ev)/*false*/;
            case MotionEvent.ACTION_UP:
                Logger.e("_____________CustomViewGroup_dispatchTouchEvent_ACTION_UP 被调用");
                return super.dispatchTouchEvent(ev)/*true*/;
            default:
        }
        return super.dispatchTouchEvent(ev)/*true*/;
    }
}
