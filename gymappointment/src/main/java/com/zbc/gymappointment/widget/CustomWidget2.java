package com.zbc.gymappointment.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomWidget2 extends FrameLayout {


    public CustomWidget2(@NonNull Context context) {
        super(context);
    }

    public CustomWidget2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWidget2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        getChildAt(0).layout(200, 200, 560, 560);
    }

}



