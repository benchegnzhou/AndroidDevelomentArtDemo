package com.zbc.androiddevelomentartdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by benchengzhou on 2019/6/16  15:59 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 自定义view
 * 类    名： CustomTextView
 * 备    注：
 */

public class CustomTextView extends View implements ViewTreeObserver.OnPreDrawListener {
    private int defaultWidth = 100;
    private int defaultHeight = 100;
    private String mText = "";
    private int mTextColor = 0xff666666;
    private int mTextSize = 18;
    private int mWidth;
    private int mHeight;
    private int mLineSpacing = 6;


    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 测量控件的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onWidthMeassure(widthMeasureSpec);
        onHeightMeassure(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int onHeightMeassure(int heightMeasureSpec) {
        mHeight = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                mHeight = size;
                break;
            case MeasureSpec.AT_MOST:
//                height = Math.min(defaultHeight, size);
                //获取文本的和padding的宽高作为控件的宽高
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                mHeight = getTextHeight(paddingTop, paddingBottom);

                break;
            //对象可以无限的大，不受限制
            case MeasureSpec.UNSPECIFIED:
                mHeight = Math.min(defaultHeight, size);
                break;
            default:
        }
        return mHeight;

    }

    /**
     * 获取控件的高度
     */
    private int getTextHeight(int paddingTop, int paddingBottom) {
        int height = 0;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mTextColor);
        paint.setTextSize(mTextSize);
        Rect rect = new Rect();
        paint.getTextBounds(mText, 0, mText.length() - 1, rect);

        //判断文字的行数
        if (!TextUtils.isEmpty(mText)) {
            //超过一行
            if (mWidth < rect.width()) {
                int lineCount = rect.width() / mWidth + (rect.width() % mWidth == 0 ? 0 : 1);
                height = lineCount * rect.height() + ((lineCount > 1 ? 0 : lineCount - 1) * mLineSpacing);
            } else {
                height = rect.height();
            }
        }
        return height + paddingTop + paddingBottom;
    }

    private int onWidthMeassure(int widthMeasureSpec) {
        mWidth = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                mWidth = size;
                break;
            case MeasureSpec.AT_MOST:
                int paddingLeft = getPaddingLeft();
                int paddingRight = getPaddingRight();
                mWidth = Math.min(defaultWidth, size);
                break;
            //对象可以无限的大，不受限制
            case MeasureSpec.UNSPECIFIED:
                mWidth = Math.min(defaultWidth, size);
                break;
            default:
        }
        return mWidth;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建bitmap用于报错bitmap图像,后续的绘图信息可以从里面直接取出并恢复
//        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), null);
//        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mTextColor);
        paint.setTextSize(mTextSize);
        Rect rect = new Rect();
        paint.getTextBounds(mText, 0, mText.length() - 1, rect);
        canvas.drawText(mText, 0 /*getPaddingLeft()*/, rect.height(), paint);
    }


    /**
     * 设置文本
     *
     * @param text
     */
    public CustomTextView setText(CharSequence text) {
        if (text == null) {
            mText = "";
        } else {
            mText = text.toString();
        }
        invalidate();
        return this;
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public CustomTextView setText(String text) {
        if (text == null) {
            mText = "";
        } else {
            mText = text;
        }
        requestLayout();
        return this;
    }


    /**
     * 设置文本的颜色
     *
     * @param textColor
     */
    public CustomTextView settextColor(int textColor) {
        mTextColor = textColor;
        requestLayout();
        return this;
    }


    /**
     * 实现textView数据刷新
     */
    public CustomTextView invalidateTextView() {
        requestLayout();
        return this;
    }


    /**
     * 设置文本的大小
     *
     * @param textSize
     * @return
     */
    public CustomTextView setTextSize(int textSize) {
        mTextSize = textSize < 0 ? 0 : textSize;
        return this;
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }
}
