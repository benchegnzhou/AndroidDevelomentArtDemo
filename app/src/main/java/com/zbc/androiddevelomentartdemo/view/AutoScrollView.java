package com.zbc.androiddevelomentartdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.ztsc.commonutils.logcat.LogUtil;


/**
 * Created by benchengzhou on 2019/4/16  10:27 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 自动滚动的view
 * 类    名： AutoScrollView
 * 备    注：
 */

public class AutoScrollView extends AppCompatImageView {

    private Context mContext;
    private int flag = 0;
    private Scroller mScroller;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != 100) {
                return;
            }


            if (getScrollX() < -600) {
                flag = 0;
            } else if (getScrollX() > 600) {
                flag = 1;
            }


            if (flag == 0) {
//                AutoScrollView.this.scrollBy(1, 1);
                AutoScrollView.this.scrollTo(getScrollX() + 1, getScrollY() + 1);
            } else {
//                AutoScrollView.this.scrollBy(-1, -1);
                AutoScrollView.this.scrollTo(getScrollX() - 1, getScrollY() - 1);
            }

            handler.sendEmptyMessageDelayed(100, 10);
        }

    };


    public AutoScrollView(Context context) {
        this(context, null);
    }

    public AutoScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mScroller = new Scroller(mContext);
    }


    public void scrollLeft() {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessage(100);
    }


    public void smoothScroll() {
        smoothScrollTo(600, 900);
    }


    // 缓慢滚动到指定位置
    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 4000ms内滑向destX，效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, delta, 0, 4000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    public void release() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

    }

//    @Override
 /* public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.e("_____________ dispatchTouchEvent_ACTION_DOWN 被调用");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e("_____________ dispatchTouchEvent_ACTION_MOVE 被调用");
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e("_____________ dispatchTouchEvent_ACTION_UP 被调用");
                break;
            default:
        }

        return super.dispatchTouchEvent(ev);
    }*/


    private int startX = 0;
    private int startY = 0;
    private int viewStartX = 0;
    private int viewStartY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                viewStartX = getScrollX();
                viewStartY = getScrollY();
                LogUtil.e("_____________AutoScrollView_onTouchEvent_ACTION_DOWN 被调用");
                return /*true*/super.onTouchEvent(event) ;
            case MotionEvent.ACTION_MOVE:
                int difX = (int) (startX - event.getX());
                int difY = (int) (startY - event.getY());

                AutoScrollView.this.scrollTo(viewStartX + difX, viewStartY + difY);
                LogUtil.e("_____________AutoScrollView_onTouchEvent_ACTION_MOVE 被调用");
                boolean dealWith = Math.abs(difX) < 200 && Math.abs(difY) < 200;
                LogUtil.e("_____________AutoScrollView_onTouchEvent_ACTION_MOVE " + (dealWith ? "将事件自己处理" : "将事件交给父类处理"));
                return super.onTouchEvent(event);

            case MotionEvent.ACTION_UP:
                LogUtil.e("_____________AutoScrollView_onTouchEvent_ACTION_UP 被调用");
                return super.onTouchEvent(event);
            default:
        }

        return false;
    }




    private int startDownX=0;
    private int startDownY=0;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startDownX = (int) event.getX();
                startDownY = (int) event.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int difX = (int) (startX - event.getX());
                int difY = (int) (startY - event.getY());
                boolean intercept = Math.abs(difX) < 200 && Math.abs(difY) < 200;
                getParent().requestDisallowInterceptTouchEvent(intercept);
                break;
            case MotionEvent.ACTION_UP:
                break;
                default:
        }

        return super.dispatchTouchEvent(event);
    }
}