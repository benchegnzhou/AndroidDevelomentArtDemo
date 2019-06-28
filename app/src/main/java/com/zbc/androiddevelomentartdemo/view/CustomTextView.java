package com.zbc.androiddevelomentartdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

/**
 * Created by benchengzhou on 2019/6/16  15:59 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 自定义view
 * 类    名： CustomTextView
 * 备    注：
 */

public class CustomTextView extends View implements ViewTreeObserver.OnPreDrawListener {
    private int defaultWidth = 0;
    private int defaultHeight = 100;
    private String mText = "";
    private int mTextColor = 0xff666666;
    private int mTextSize = 18;
    private int mWidth;
    private int mHeight;
    private int mLineSpacing = 6;
    private int lineWidthValueMax = 0;
    private Paint mPaint;
    private Rect mMaxRect;


    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    /**
     * 画笔初始化
     */
    private void initPaint() {
        //基线
        int baseLineY = 0;
        int baseLineX = 0;

        if (mPaint == null) {
            mPaint = new Paint();
        }
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);


        //计算各线在位置
        Paint.FontMetrics fm = mPaint.getFontMetrics();

        float ascent = baseLineY + fm.ascent;//当前绘制顶线
        float descent = baseLineY + fm.descent;//当前绘制底线
        float top = baseLineY + fm.top;//可绘制最顶线
        float bottom = baseLineY + fm.bottom;//可绘制最低线

        //字符串所占的高度和宽度
        int width = (int) mPaint.measureText(mText);
        int height = (int) (bottom - top);
        //单个文字绘制时可以占据的最大矩形区域
        mMaxRect = new Rect(baseLineX, (int) (baseLineY + fm.top), (baseLineX + width), (int) (baseLineY + fm.bottom));

    }


    /**
     * 测量控件的大小,得到的是父容器的大小换句话说就是自定义控件最大可以使用的大小
     * 经过测试如果父控件固定大小match_parent，子控件页同样的使用确定大小的数值1040
     * 返回mode=MeasureSpec.EXACTLY: 大小为子控件的设置数值大小size=1040，结论子控件为确切值时，子控件onMeasure获得设置的确切值
     * <p>
     * 设置父控件1000，子控件设置1040
     * 返回mode=MeasureSpec.EXACTLY: 大小为子控件的设置数值大小size=1040
     * <p>
     * <p>
     * 设置父控件大小为wrap_content，子控件设置1040
     * 返回mode=MeasureSpec.EXACTLY: 大小为子控件的设置数值大小size=1040
     * <p>
     * 设置父控件大小为match_parent，子控件设置wrap_content
     * 返回mode=MeasureSpec.AT_MOST: 大小为子控件的设置数值大小size=父控件的剩余空间1080-marginLeft-marginRight
     * <p>
     * <p>
     * 设置父控件大小为wrap_content，子控件设置wrap_content
     * 返回mode=MeasureSpec.AT_MOST: 大小为子控件的设置数值大小size=父控件的剩余可用空间1080-marginLeft-marginRight
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initPaint();
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
                // height = Math.min(defaultHeight, size);
                //获取文本的和padding的宽高作为控件的宽高

                mHeight = getTextHeight();

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
     * 控件宽度测量
     *
     * @param widthMeasureSpec
     * @return
     */
    private int onWidthMeassure(int widthMeasureSpec) {
        mWidth = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);


        int padding = getPaddingLeft() + getPaddingRight();


        switch (mode) {
            case MeasureSpec.EXACTLY:
                mWidth = size;
                break;
            case MeasureSpec.AT_MOST:
                int want = size - padding;
                mWidth = getTextWidth(want);
                break;
            //对象可以无限的大，不受限制
            case MeasureSpec.UNSPECIFIED:
                Rect rect = new Rect();
                mPaint.getTextBounds(mText, 0, mText.length() - 1, rect);
                mWidth = rect.width() + padding;
                break;

            default:
        }
        return mWidth;
    }


    /**
     * 获取控件的宽度
     *
     * @param want
     */
    private int getTextWidth(int want) {
        int width = 0;
        Rect rect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length() - 1, rect);
        //有空间并且一行可以乘下,需要绘制文字
        if (want > 0 && rect.width() <= want) {
            width = rect.width();
        } else if (want > 0 && rect.width() > want) {
            width = want;
        }
        return width;
    }

    ArrayList<String> mTextList = new ArrayList<>();

    /**
     * 获取控件的高度
     */
    private int getTextHeight() {
        int height = 0;
        int baseX = 0;
        int baseY = 0;
        mTextList.clear();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        double lineWidthMax = Math.max(mWidth - getPaddingLeft() - getPaddingRight(), 0.01);
        if (mText == null || mText.length() == 0) {
            return 0;
        }
        Rect rect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length() - 1, rect);

        //一行显示
        if (rect.width() <= lineWidthMax) {
            mTextList.add(mText);
            return rect.height() + getPaddingTop() + getPaddingBottom();
        }

        double lineNum = rect.width() * 1f / lineWidthMax;
        //  每行文字个数
        int onelineTexts = (int) (mText.length() / lineNum + 0.5f);
        String textRemain = mText;

        while (!TextUtils.isEmpty(textRemain)) {

            if (onelineTexts > textRemain.length()) {//不到一行
                mTextList.add(textRemain);
                textRemain = "";
            } else {
                for (int i = onelineTexts; i < textRemain.length() - 1; i++) {
                    if (i == textRemain.length() - 1) {
                        mTextList.add(textRemain);
                        textRemain = "";
                        break;
                    }
                    Rect rectF = new Rect();
                    String remianTempF = textRemain.substring(0, i);
                    String remianTempL = textRemain.substring(0, i + 1);
                    mPaint.getTextBounds(remianTempF, 0, remianTempF.length() - 1, rectF);
                    Rect rectL = new Rect();
                    mPaint.getTextBounds(remianTempL.substring(0, i), 0, remianTempL.length() - 1, rectL);
                    float widthF = mPaint.measureText(remianTempF);
                    float widthL = mPaint.measureText(remianTempL);


                    if (widthF > lineWidthMax) {  //减小字数
                        i = Math.max(1, onelineTexts - 5);
                    } else if (widthF <= lineWidthMax && widthL >= lineWidthMax) {
                        mTextList.add(textRemain.substring(0, i));
                        textRemain = textRemain.substring(i);
                        break;
                    }
                }
            }

        }
        return (rect.height() + mLineSpacing) * mTextList.size() + paddingTop + paddingBottom;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 创建一个bitmap位图存储上一次的图像信息
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算各线在位置
        Paint.FontMetrics fm = mPaint.getFontMetrics();

        //创建bitmap用于报错bitmap图像,后续的绘图信息可以从里面直接取出并恢复
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        canvas.drawBitmap(bitmap, 0, 0, null);
        if (mTextList == null || mTextList.size() == 0) {
            return;
        }
        Rect rect = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length() - 1, rect);

        Paint paint = new Paint();
        paint.setColor(0xff000000);
        paint.setStrokeWidth(2);
        for (int i = 0; i < mTextList.size(); i++) {
            String text = mTextList.get(i);
            int lineLeft = getPaddingLeft();
            float lineTop = getPaddingTop() + i * (mLineSpacing + rect.height()); //- fm.descent * (i + 1)

            canvas.drawText(text, lineLeft, rect.height() + lineTop, mPaint);

            Rect rect2 = new Rect();
            mPaint.getTextBounds(text, 0, text.length() - 1, rect2);
            //   canvas.drawRect(rect2,paint);
            canvas.drawLine(lineLeft, lineTop, lineLeft, rect.height() + lineTop, paint);
            canvas.drawLine(lineLeft, rect.height() + lineTop + fm.descent, rect.width(), rect.height() + lineTop + fm.descent , paint);
        }

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
