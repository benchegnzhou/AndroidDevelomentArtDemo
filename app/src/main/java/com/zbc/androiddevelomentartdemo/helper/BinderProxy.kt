package com.zbc.androiddevelomentartdemo.helper

import android.os.IBinder
import android.os.Parcel
import android.os.RemoteException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zbc.androiddevelomentartdemo.service.IMessageServer
import com.ztsc.commonutils.logcat.Logger
import kotlin.jvm.Throws

/**
 * 为了隐藏Binder内部的调用，让业务无感知，
 * 一般会封装一个代理层，影藏内部的实现
 * https://www.jianshu.com/p/0fff33c09f34
 */
class BinderProxy(var mIBinder: IBinder?) : ViewModel() {
    // Create a LiveData with a String
    val mCurrentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun sendMessage(message: String?) {
        if (!checkAvailable()) {
            return
        }
        handle { data, reply ->
            //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
            data.writeInterfaceToken(IMessageServer.DESCRIPTOR)
            data.writeString(message)
            // https://www.jianshu.com/p/0fff33c09f34
            mIBinder?.transact(IMessageServer.TRANSACTION_callServiceMethod, data, reply, 0)
            //读取服务端的处理异常
            reply.readException()
            val result = reply.readString()
            Logger.e("---", "接收到数据  = $result")
            mCurrentName.value = "信息打印如下： \n$result"
        }
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


    fun method1(message: String?) {
        if (!checkAvailable()) {
            return
        }
        handle { data, reply ->
            //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
            data.writeInterfaceToken(IMessageServer.DESCRIPTOR)
            data.writeString(message)
            // https://www.jianshu.com/p/0fff33c09f34
            mIBinder?.transact(IMessageServer.TRANSACTION_method_1, data, reply, 0)
            //读取服务端的处理异常
            reply.readException()
            val result = reply.readString()
            mCurrentName.value = "信息打印如下： \n$result"
            Logger.e("---", "接收到数据  = $result")
        }
    }

    fun method2(arg1: Int, arg2: Int): Int {
        var result = 0
        if (!checkAvailable()) {
            return 0
        }
        handle { data, reply ->
            //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
            data.writeInterfaceToken(IMessageServer.DESCRIPTOR)
            data.writeInt(arg1)
            data.writeInt(arg2)
            // https://www.jianshu.com/p/0fff33c09f34
            mIBinder?.transact(IMessageServer.TRANSACTION_method_2, data, reply, 0)
            //读取服务端的处理异常
            reply.readException()
            mCurrentName.value = "信息打印如下： \n${reply.readString()}"
            result = reply.readInt()
        }
        return result
    }

    fun registerNotifyBinder(binder: IBinder) {
        if (!checkAvailable()) {
            return
        }
        handle { data, reply ->
            //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
            data.writeInterfaceToken(IMessageServer.DESCRIPTOR)
            data.writeStrongBinder(binder)
            // https://www.jianshu.com/p/0fff33c09f34
            mIBinder?.transact(IMessageServer.TRANSACTION_registerNotifyBinder, data, reply, 0)
            //读取服务端的处理异常
            reply.readException()
            val result = reply.readString()
            mCurrentName.value = "信息打印如下： \n$result"
            Logger.e("---", "接收到数据  = $result")
        }
    }

    fun unRegisterService() {
        if (!checkAvailable()) {
            return
        }
        handle { data, reply ->
            //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
            data.writeInterfaceToken(IMessageServer.DESCRIPTOR)
            // https://www.jianshu.com/p/0fff33c09f34
            mIBinder?.transact(IMessageServer.TRANSACTION_unregisterServiceNotify, data, reply, 0)
            //读取服务端的处理异常
            reply.readException()
            val result = reply.readString()
            mCurrentName.value = "信息打印如下： \n$result"
            Logger.e("---", "接收到数据  = $result")
        }
    }

    fun requestMemoryFile() {
        if (!checkAvailable()) {
            return
        }
        handle { data, reply ->
            //因为是通过parcelable传递的数据，所以写入数据的顺序和大区数据的顺序一定要一致，任何信息，数量不一致和类型不一致都会error
            data.writeInterfaceToken(IMessageServer.DESCRIPTOR)
            // https://www.jianshu.com/p/0fff33c09f34
            mIBinder?.transact(IMessageServer.TRANSACTION_requestMemoryFile, data, reply, 0)
            //读取服务端的处理异常
            reply.readException()
            val result = reply.readString()
            mCurrentName.value = "信息打印如下： \n$result"
            Logger.e("---", "接收到数据  = $result")
        }
    }


    fun release() {
        mIBinder = null
    }

    private fun checkAvailable(): Boolean {
        return mIBinder?.isBinderAlive ?: false
    }

}