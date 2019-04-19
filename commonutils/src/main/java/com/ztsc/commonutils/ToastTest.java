package com.ztsc.commonutils;

import android.content.Context;
import android.widget.Toast;

public class ToastTest {
    /*
     * 定义的土司工具类,实现单例的可以连续的弹出内容的土司
     */
    public static Toast toast;
    public static boolean isShowToast = false;
    public static Context sContext;

    /**
     * 全局初始化一次
     */
    public void Init(Context context) {
        if (context == null) {
            new Throwable("the context can't be null");
        }
        sContext = context.getApplicationContext();
    }

    /**
     * 用户提示吐司
     *
     * @param text
     */
    public static void showToastShort(String text) {
        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }

    /**
     * 用户提示吐司
     *
     * @param text
     */
    public static void showToastLong(String text) {
        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(sContext, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }

    /**
     * 调试时短吐司
     *
     * @param text
     */
    public static void showDebugToastShort(String text) {
        if (!isShowToast) {
            return;
        }

        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(sContext, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }

    /**
     * 调试时长吐司
     *
     * @param text
     */
    public static void showDebugToastLong(String text) {
        if (!isShowToast) {
            return;
        }


        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(sContext, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }


    /**
     * 取消所有的吐司
     */
    public static void cancleAllToast() {
        if (toast != null) {
            toast.cancel();
        }


    }

}
