package com.ztsc.commonutils.toast;
import android.widget.Toast;
import com.ztsc.commonutils.CommonUtil;

/**
 * Created by benchengzhou on 2018/11/19  15:08 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 自定义toast
 * 类    名： ToastUtils
 * 备    注：
 */

public class ToastUtils {

    /*
     * 定义的土司工具类,实现单例的可以连续的弹出内容的土司
     */
    public static Toast toast;
    public static boolean isShowToast = CommonUtil.getInstance().getConfig().ToastOpen;

    /**
     * 用户提示吐司
     * @param text
     */
    public static void showToastShort(String text) {
        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(CommonUtil.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }

    /**
     * 用户提示吐司
     * @param text
     */
    public static void showToastLong(String text) {
        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(CommonUtil.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }

    /**
     * 调试时短吐司
     * @param text
     */
    public static void showDebugToastShort(String text) {
        if(!isShowToast){
            return;
        }

        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(CommonUtil.getInstance().getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }

    /**
     * 调试时长吐司
     * @param text
     */
    public static void showDebugToastLong(String text) {
        if(!isShowToast){
            return;
        }


        // 如果toast为空就创建
        if (toast == null) {
            toast = Toast.makeText(CommonUtil.getInstance().getApplicationContext(), text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text); // 如果不为空就修改显示的内容
        }
        // 显示土司
        toast.show();
    }


    /**
     * 取消所有的吐司
     */
    public static void cancleAllToast(){
        if (toast != null) {
            toast.cancel();
        }
    }



}