package com.ztsc.commonutils.deviceutil

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

/**
 * 网络工具类
 */
class NetWorkUtil {
    companion object {
        val TYPE_NOT_CONNECTION = 0
        val TYPE_WIFI_CONNECTION = 1
        val _2G_CONNECTION = 2
        val _3G_CONNECTION = 3
        val _4G_CONNECTION = 4
        val _5G_CONNECTION = 5
        val TYPE_MOBILE_CONNECTION = 6


        /**
         * 判断是否有网络连接
         * 判断当前网络是否是wifi网络
         * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
         *
         * @param context
         * @return boolean
         */
        fun netWorkType(context: Context): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) netWorkTypeHighApiVersion(
                context
            ) else netWorkTypeMinorApiVersion(context)
        }

        /**
         * 判断是否有网络连接
         * 判断当前网络是否是wifi网络
         * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
         *
         * @param context
         * @return boolean
         */
        fun netWorkTypeMinorApiVersion(context: Context): Int {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo: NetworkInfo? = connectivityManager.getActiveNetworkInfo()
            if (activeNetInfo == null || !activeNetInfo.isAvailable) {
                return TYPE_NOT_CONNECTION
            } else if (activeNetInfo.type == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI_CONNECTION
            } else if (activeNetInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE_CONNECTION
            }
            return TYPE_NOT_CONNECTION
        }

        /**
         * API >= 21需要使用下面这一个
         */
        @TargetApi(Build.VERSION_CODES.M)
        fun netWorkTypeHighApiVersion(context: Context): Int {
            val connectivityManager: ConnectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities: NetworkCapabilities? =
                connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())
            if (networkCapabilities == null) {
                //TODO "请打开网络"
                return TYPE_NOT_CONNECTION
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                //TODO "当前使用移动网络"
                return TYPE_WIFI_CONNECTION
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                //TODO  System.out.println("当前使用WIFI网络")
                return TYPE_MOBILE_CONNECTION
            }
            return TYPE_NOT_CONNECTION
        }
    }
}