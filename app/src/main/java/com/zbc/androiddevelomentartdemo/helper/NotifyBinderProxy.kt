package com.zbc.androiddevelomentartdemo.helper

import android.os.IBinder
import android.os.Parcel
import com.zbc.androiddevelomentartdemo.content.ContentValue
import com.zbc.androiddevelomentartdemo.service.IMessageServer
import com.zbc.androiddevelomentartdemo.service.IServiceNotify
import com.ztsc.commonutils.logcat.Logger

/**
 * 客户端服务的包装类
 */
class NotifyBinderProxy(var mIBinder: IBinder?) : IServiceNotify {

    override fun registerService() {

    }

    override fun unRegisterService() {

    }

    override fun callServiceMethod() {

    }

    fun handle(block: (_data: Parcel, _reply: Parcel) -> Unit) {
        try {
            val data = Parcel.obtain()
            val reply = Parcel.obtain()
            try {
                block(data, reply)
            } catch (e: java.lang.Exception) {
                Logger.e("sendMessageToService error!", e)
            } finally {
                data.recycle()
                reply.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun sendMessage(message: String?) {
        if (!checkAvailable()) {
            return
        }
        handle { data, reply ->
            //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
            data.writeInterfaceToken(IServiceNotify.DESCRIPTOR)
            data.writeString(message)
            // https://www.jianshu.com/p/0fff33c09f34
            mIBinder?.transact(IServiceNotify.TRANSACTION_sendMessage, data, reply, 0)
            //读取服务端的处理异常
            reply.readException()
            val result = reply.readString()
            Logger.e("---", "接收到数据  = $result")
        }
    }

    override fun transmitMemoryFile(byteArray: ByteArray?) {
        if (!checkAvailable()) {
            return
        }
        MemoryFileHelper.sendToFdServer(
            mIBinder,
            ContentValue.KEY_FD,
            byteArray,
            byteArray?.size ?: 0,
            0,
            0
        ) { _, bundle ->
            handle { data, reply ->
                //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
                data.writeInterfaceToken(IServiceNotify.DESCRIPTOR)
                data.writeBundle(bundle)
                // https://www.jianshu.com/p/0fff33c09f34
                mIBinder?.transact(IServiceNotify.TRANSACTION_transmitMemoryFile, data, reply, 0)
                //读取服务端的处理异常
                reply.readException()
                Logger.e("信息打印如下： \n${reply.readString()}")
            }
        }
    }

    override fun asBinder(): IBinder {
        return mIBinder!!
    }

    private fun checkAvailable(): Boolean {
        return mIBinder?.isBinderAlive ?: false
    }
}