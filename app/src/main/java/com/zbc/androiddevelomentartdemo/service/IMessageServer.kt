package com.zbc.androiddevelomentartdemo.service

import android.os.*
import com.ztsc.commonutils.logcat.Logger
import kotlin.jvm.Throws

//1. 创建服务端接口
interface IMessageServer : IInterface {
    //定义要求子类实现的接口
    @Throws(RemoteException::class)
    fun registerService()

    @Throws(RemoteException::class)
    fun unRegisterService()

    @Throws(RemoteException::class)
    fun method1(message: String?)

    @Throws(RemoteException::class)
    fun method2(arg1: Int, arg2: Int): Int

    @Throws(RemoteException::class)
    fun sendMessage(message: String?)

    @Throws(RemoteException::class)
    fun registerNotifyBinder(binder: IBinder)

    @Throws(RemoteException::class)
    fun requestMemoryFile()

    //3. 定义相关的传输协议
    companion object {
        const val DESCRIPTOR = "com.zbc.androidDevelopmentArtDemo.IMessageServer"

        // 数据标识最好介于FIRST_CALL_TRANSACTION - LAST_CALL_TRANSACTION 之间
        const val TRANSACTION_registerServiceNotify = Binder.FIRST_CALL_TRANSACTION + 1
        const val TRANSACTION_unregisterServiceNotify = Binder.FIRST_CALL_TRANSACTION + 2
        const val TRANSACTION_callServiceMethod = Binder.FIRST_CALL_TRANSACTION + 3
        const val TRANSACTION_method_1 = Binder.FIRST_CALL_TRANSACTION + 4
        const val TRANSACTION_method_2 = Binder.FIRST_CALL_TRANSACTION + 5
        const val TRANSACTION_requestMemoryFile = Binder.FIRST_CALL_TRANSACTION + 6
        const val TRANSACTION_registerNotifyBinder = Binder.FIRST_CALL_TRANSACTION + 7
    }

    //2. 服务端创建Binder对象的实现类
    abstract class Stub() : Binder(), IMessageServer {

        init {
            attachInterface(
                this,
                DESCRIPTOR
            )
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            when (code) {
                TRANSACTION_unregisterServiceNotify -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    unRegisterService()
                    reply?.writeNoException()
                    reply?.writeString("服务端回复！\n服务已结束！")
                    //成功处理相应后返回true
                    return true
                }
                TRANSACTION_callServiceMethod -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    val data = data.readString()
                    Logger.e("---", "服务端接受到数据了！ =  $data")
                    reply?.writeNoException()
                    reply?.writeString("服务端回复！\n老子接收到数据了！ 收到的数据为 = $data")
                    //成功处理相应后返回true
                    return true
                }
                TRANSACTION_method_1 -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    val data = data.readString()
                    method1(data)
                    reply?.writeNoException()
                    reply?.writeString("我是服务端,老子接收到了  数据为 = $data")
                    //成功处理相应后返回true
                    return true
                }
                TRANSACTION_method_2 -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    //按写入的顺序读取数据
                    val result = method2(data.readInt(), data.readInt())
                    reply?.writeNoException()
                    reply?.writeString("我是服务端老子接收到了,计算了这两个目标数的乘积是 $result")
                    reply?.writeInt(result)
                    //成功处理相应后返回true
                    return true
                }
                TRANSACTION_registerNotifyBinder -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    //按写入的顺序读取数据
                    registerNotifyBinder(data.readStrongBinder())
                    reply?.writeNoException()
                    reply?.writeString("notifyBinder注册成功！")
                    //成功处理相应后返回true
                    return true
                }
                TRANSACTION_requestMemoryFile -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    requestMemoryFile()
                    reply?.writeNoException()
                    reply?.writeString("文件请求成功！发送中请稍后！")
                    //成功处理相应后返回true
                    return true
                }
            }
            return super.onTransact(code, data, reply, flags)
        }

        override fun isBinderAlive(): Boolean {
            return super.isBinderAlive()
        }

        override fun asBinder(): IBinder {
            return this
        }
    }


}