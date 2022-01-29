package com.zbc.androiddevelomentartdemo.service

import android.app.Service
import android.content.Intent
import android.os.*
import com.zbc.androiddevelomentartdemo.R
import com.zbc.androiddevelomentartdemo.content.ContentValue
import com.zbc.androiddevelomentartdemo.entity.SystemMsgBean
import com.zbc.androiddevelomentartdemo.helper.NotifyBinderProxy
import com.ztsc.commonutils.bitmap.BitmapUtils
import com.ztsc.commonutils.logcat.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import kotlin.coroutines.CoroutineContext
import kotlin.math.log

/**
 * Created by benchengzhou on 2022/1/13  14:12 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 服务service
 * 类    名： ServerService
 * 备    注：
 */
class MessageServerService : Service() {
    private var mClientBinder: IBinder? = null
    private var mNotifyBinderProxy: NotifyBinderProxy? = null

    // 4. 创建对应的Binder实例,Server端真正实现业务的方法
    private val mBinder = object : IMessageServer.Stub() {

        override fun registerService() {

        }

        override fun unRegisterService() {
            this@MessageServerService.stopSelf()
        }

        override fun method1(message: String?) {

        }

        override fun method2(arg1: Int, arg2: Int): Int {
            return arg1 * arg2
        }

        override fun sendMessage(message: String?) {
            Logger.e("---", message ?: "")
        }

        override fun registerNotifyBinder(binder: IBinder) {
            mClientBinder = binder
            mNotifyBinderProxy = NotifyBinderProxy(mClientBinder)
        }

        override fun requestMemoryFile() {
            val byteArray = getByteArray()
            byteArray?.apply {
                mNotifyBinderProxy?.transmitMemoryFile(byteArray)
            }
        }
    }

    private fun getByteArray(): ByteArray? {
        var byteArray: ByteArray? = null
        val inputStream: InputStream? = resources.openRawResource(R.drawable.image_a1)
        inputStream?.apply {
            byteArray = BitmapUtils.InputStream2Bytes(this)
        }
        return byteArray
    }

    override fun onDestroy() {
        mClientBinder = null
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        val bundle: Bundle = intent.extras as Bundle
        val systemMsgBean: SystemMsgBean? = bundle.getParcelable("systemMsgBean") as SystemMsgBean?
        Logger.e("onBind 接收到的消息为————————\n" + systemMsgBean?.toString() ?: "")

        //模拟服务端推送消息给客户端
        GlobalScope.launch(Dispatchers.IO) {
            do {
                delay(3000)
                mNotifyBinderProxy?.sendMessage("来自服务的的数据推送")
                Logger.e("send message!")
            } while (mClientBinder != null)
        }
        // 4. 返回对象给客户端
        return mBinder.asBinder()
    }


}