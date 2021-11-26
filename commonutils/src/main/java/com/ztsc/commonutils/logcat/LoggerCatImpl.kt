package com.ztsc.commonutils.logcat

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import com.ztsc.commonutils.commonutil.CommonUtils
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class LoggerCatImpl {
    private val sTag: MutableMap<Int, String> = ConcurrentHashMap()

    companion object {
        private val sThread by lazy { HandlerThread("LogThread") }
        private val sLogHandler: Handler by lazy {
            object : Handler(sThread.looper) {
                override fun handleMessage(msg: Message) {
                    when (msg.what) {

                    }
                    msg.recycle()
                }
            }
        }

//        init {
//            sThread.start()
//            sLogHandler =
//
//        }
//
//
//        fun logThread(tag: String, log: String?) {
//            val msg = sLogHandler!!.obtainMessage(0)
//            msg.obj = log
//            msg.arg1 = tag.hashCode()
//            sTag[msg.arg1] = tag
//            msg.sendToTarget()
//        }

    }

    private constructor()

    @Volatile
    private var sIsLogEnabled = false

    private val COMMA = ", "

    fun getLogEnableInfo() {
        var logLevel: String? = ""
        try {
            logLevel = CommonUtils.readProp("log.tag.folme.level")
            logLevel = logLevel ?: ""
        } catch (e: Exception) {
            Log.i(CommonUtils.TAG, "can not access property log.tag.folme.level, no log", e)
        }
        Log.d(CommonUtils.TAG, "logLevel = $logLevel")
        sIsLogEnabled = logLevel == "D"
    }

    fun isLogEnabled(): Boolean {
        return sIsLogEnabled
    }

    fun debug(message: String, vararg objArray: Any?) {
        if (!sIsLogEnabled) {
            return
        }
        if (objArray.size > 0) {
            val sb = StringBuilder(COMMA)
            val initLength = sb.length
            for (obj in objArray) {
                if (sb.length > initLength) {
                    sb.append(COMMA)
                }
                sb.append(obj)
            }
            Log.i(CommonUtils.TAG, message + sb.toString())
        } else {
            Log.i(CommonUtils.TAG, message)
        }
    }

    fun getStackTrace(length: Int): String? {
        val traces = Thread.currentThread().stackTrace
        val count = Math.min(traces.size, 4 + length)
        return Arrays.toString(Arrays.asList(*traces).subList(3, count).toTypedArray())
    }


}