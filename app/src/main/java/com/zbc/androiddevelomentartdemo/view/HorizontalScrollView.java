package com.zbc.androiddevelomentartdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.ztsc.commonutils.logcat.LogUtil;

/**
 * Created by benchengzhou on 2019/4/19  13:50 .
 * 作者邮箱： mappstore@163.com
 * 功能描述：
 * 类    名： HorizontalScrollView
 * 备    注：
 */

public class HorizontalScrollView extends android.widget.HorizontalScrollView {

    private Scroller mScroller;
    private int mLastXIntercept;
    private int mLastYIntercept;
    private VelocityTracker mVelocityTracker;
    private int mLastX;
    private int mLastY;
    private int mChildWidth = 1080;
    private int mChildIndex = 1;
    private int mChildrenSize = 3;
    private float mDownX;
    private float mDownY;
    private int mStartX;
    private float startX;
    private float startY;

    public HorizontalScrollView(Context context) {
        this(context, null);
    }

    public HorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
    }

   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastXIntercept;
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }
        LogUtil.d("—————————— HorizontalScrollView intercepted=" + intercepted);
        mLastX = x;
        mLastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }*/

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(-deltaX, 0);
                break;
            }
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildWidth;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize -
                        1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx, 0);
                mVelocityTracker.clear();
                break;
            }
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }*/


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mDownX = event.getX();
                mDownY = event.getY();
                mStartX = getScrollX();
                if (Math.abs(mDownX - startX) > Math.abs(mDownY - startY)) {
                    intercept = true;
                    requestDisallowInterceptTouchEvent(true);
                } else {
                    intercept = false;
                    requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                mVelocityTracker.clear();
                break;
            default:

        }

        LogUtil.e("————————— HorizontalScrollView  onInterceptTouchEvent  " + (intercept ? "拦截了当前事件" : "未拦截事件"));
        return intercept;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.e("______ HorizontalScrollView  onTouchEvent  ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.e("______ HorizontalScrollView  onTouchEvent  ACTION_MOVE");
                int difX = (int) (event.getX() - mDownX);
                LogUtil.e("------ HorizontalScrollView  onTouchEvent  currentX  " + (-mStartX - difX));
                //scrollTo(mStartX - difX, getScrollY());
                smoothScrollBy1(-mStartX - difX, 0, 1);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.e("______ HorizontalScrollView  onTouchEvent  ACTION_UP");
                mVelocityTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) mVelocityTracker.getXVelocity();
                LogUtil.e("当前控件滑动速度是 " + xVelocity);
                // smoothScrollBy1(-(int) (xVelocity * 0.3f), 0,2000);
                break;
            default:
        }

        return super.onTouchEvent(event);
    }

    private void smoothScrollBy1(int dx, int dy, int time) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, time);
        invalidate();
    }


    // 缓慢滚动到指定位置
    public void smoothScrollTo2(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;
        // 1000ms内滑向destX，效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, deltaX, 0, 1000);
        invalidate();
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

}