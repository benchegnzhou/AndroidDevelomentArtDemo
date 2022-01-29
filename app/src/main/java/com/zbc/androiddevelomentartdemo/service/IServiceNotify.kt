package com.zbc.androiddevelomentartdemo.service

import android.os.*
import com.zbc.androiddevelomentartdemo.content.ContentValue
import com.zbc.androiddevelomentartdemo.helper.MemoryFileHelper
import com.zbc.androiddevelomentartdemo.helper.NotifyBinderProxy
import kotlin.jvm.Throws


//1. 创建服务端接口
interface IServiceNotify : IInterface {
    //定义要求子类实现的接口
    @Throws(RemoteException::class)
    fun registerService()

    @Throws(RemoteException::class)
    fun unRegisterService()

    @Throws(RemoteException::class)
    fun callServiceMethod()

    @Throws(RemoteException::class)
    fun sendMessage(message: String?)

    @Throws(RemoteException::class)
    fun transmitMemoryFile(byteArray: ByteArray?)

    //3. 定义相关的传输协议
    companion object {
        const val DESCRIPTOR = "com.zbc.androidDevelopmentArtDemo.IServiceNotify"

        const val TRANSACTION_registerService = Binder.FIRST_CALL_TRANSACTION + 1
        const val TRANSACTION_unRegisterService = Binder.FIRST_CALL_TRANSACTION + 2
        const val TRANSACTION_callServiceMethod = Binder.FIRST_CALL_TRANSACTION + 3
        const val TRANSACTION_sendMessage = Binder.FIRST_CALL_TRANSACTION + 4
        const val TRANSACTION_transmitMemoryFile = Binder.FIRST_CALL_TRANSACTION + 5
    }

    //2. 服务端创建Binder对象的实现类
    abstract class Proxy() : Binder(), IServiceNotify {

        init {
            attachInterface(
                this,
                DESCRIPTOR
            )
        }

        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            when (code) {
                TRANSACTION_callServiceMethod -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    callServiceMethod()
                    reply?.writeNoException()
                    reply?.writeString("客户端回复！\ncallServiceMethod success！")
                    //成功处理相应后返回true
                    return true
                }
                TRANSACTION_sendMessage -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    sendMessage(data.readString())
                    reply?.writeNoException()
                    reply?.writeString("客户端回复！\n接收到服务端发送的数据！")
                    //成功处理相应后返回true
                    return true
                }
                TRANSACTION_transmitMemoryFile -> {
                    /**
                     * 读取由writeInterfaceToken编写的头，并验证它与相关的接口名称是否匹配。
                     * 如果出现错误的接口类型，则抛出SecurityException。
                     * 在绑定器上使用时，此异常应传播到调用者。
                     */
                    data.enforceInterface(DESCRIPTOR)
                    val readBundle = data.readBundle()
                    val params =
                        readBundle?.getSerializable(ContentValue.KEY_FD) as HashMap<String?, ParcelFileDescriptor?>
                    params?.apply {
                        val length = readBundle?.getInt(ContentValue.KEY_LENGTH) ?: 0
                        transmitMemoryFile(
                            MemoryFileHelper.readFromMemoryAsByteArray(
                                ContentValue.KEY_FD,
                                params,
                                length
                            )
                        )
                        reply?.writeNoException()
                        reply?.writeString("客户端回复！\n接收到服务端发送的文件数据！")
                    }

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