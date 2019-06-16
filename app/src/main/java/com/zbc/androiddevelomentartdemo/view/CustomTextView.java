package com.zbc.androiddevelomentartdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by benchengzhou on 2019/6/16  15:59 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 自定义view
 * 类    名： CustomTextView
 * 备    注：
 */

public class CustomTextView extends View {
    private int defaultWidth = 100;
    private int defaultHeight = 100;
    private String mText = "";
    private int mTextColor = 0xff666666;


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
        int width = onWidthMeassure(widthMeasureSpec);
        int height = onHeightMeassure(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int onHeightMeassure(int heightMeasureSpec) {
        int height = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                height = size;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(defaultHeight, size);
                break;
            //对象可以无限的大，不受限制
            case MeasureSpec.UNSPECIFIED:
                height = Math.min(defaultHeight, size);
                break;
            default:
        }
        return height;

    }

    private int onWidthMeassure(int widthMeasureSpec) {
        int width = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                width = size;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(defaultWidth, size);
                break;
            //对象可以无限的大，不受限制
            case MeasureSpec.UNSPECIFIED:
                width = Math.min(defaultWidth, size);
                break;
            default:
        }
        return width;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //创建bitmap用于报错bitmap图像,后续的绘图信息可以从里面直接取出并恢复
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), null);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint paint = new Paint();
        paint.



        canvas.drawText(mText, getPaddingLeft(), getPaddingTop(), );


    }
}
