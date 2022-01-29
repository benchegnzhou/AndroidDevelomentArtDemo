package com.ztsc.commonutils.logcat;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.net.UnknownHostException;


/**
 * http://www.flvcd.com/
 * .--,       .--,
 * ( (  \.---./  ) )
 * '.__/o   o\__.'
 * {=  ^  =}
 * >  -  <
 * /       \
 * //       \\
 * //|   .   |\\
 * "'\       /'"_.-~^`'-.
 * \  _  /--'         `
 * ___)( )(___
 * (((__) (__)))    高山仰止,景行行止.虽不能至,心向往之。
 */

/**
 * 输入log
 *
 * @author zbc
 */
public class Logger {
    static {
        Handler sHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    public static void d(String message, Exception e) {

    }

    public static void d(String tag, String message) {

    }

    public static void d(String tag, String message1, String message2, String message3) {

    }

    public static void e(String tag, String message) {
        e(tag, message,null);
    }

    public static void e(String message, Exception e) {
        e("", message, e);
    }


    public static void e(String tag, String message, Exception e) {
        Log.e(tag, message + "", e);
    }

    public static void e(String s) {
        Log.e("commonUtil", s + "");
    }

    public static void w(String tag, String s) {

    }
}
