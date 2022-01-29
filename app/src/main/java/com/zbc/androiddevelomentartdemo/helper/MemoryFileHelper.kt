package com.zbc.androiddevelomentartdemo.helper

import android.os.*
import android.util.Log
import com.zbc.androiddevelomentartdemo.content.ContentValue
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.IOException

/**
 * 共享内存写入和读取实现
 */
class MemoryFileHelper {

    companion object {
        private const val TAG = "MemoryFileHelper"

        /**
         * 写入匿名共享内存
         * @param data 目标数据
         * @param length  数据长度
         * @return 匿名共享内存对应的文件描述符
         */
        fun writeToMemory(data: ByteArray?, length: Int): ParcelFileDescriptor? {
            var parcelFileDescriptor: ParcelFileDescriptor? = null
            var memoryFile: MemoryFile? = null
            try {
                memoryFile = MemoryFile("", length)
                memoryFile.writeBytes(data, 0, 0, length)
                val method = MemoryFile::class.java.getDeclaredMethod("getFileDescriptor")
                method.isAccessible = true
                val fileDescriptor = method.invoke(memoryFile) as FileDescriptor
                parcelFileDescriptor = ParcelFileDescriptor.dup(fileDescriptor)
            } catch (ex: Exception) {
                Log.w(TAG, "catch write to memory error", ex)
            } finally {
                memoryFile?.close()
            }
            return parcelFileDescriptor
        }


        /**
         * 把ParcelFileDescriptor发送到Server端
         * @param iFloatingService
         * @param data
         * @param length
         */
        fun sendToFdServer(
            binder: IBinder?,
            KEY_FD: String,
            data: ByteArray?,
            length: Int,
            width: Int,
            height: Int, block: (binder: IBinder?, bundle: Bundle) -> Unit
        ) {
            val parcelFileDescriptor: ParcelFileDescriptor =
                writeToMemory(data, length) ?: throw Exception("writeToMemory error!")

            val params = java.util.HashMap<String, ParcelFileDescriptor>(1)
            params[KEY_FD] = parcelFileDescriptor
            val bundle = Bundle()
            bundle.putSerializable(
                KEY_FD,
                params
            )
            bundle.putInt(ContentValue.KEY_LENGTH, length)
            bundle.putInt(ContentValue.KEY_WIDTH, width)
            bundle.putInt(ContentValue.KEY_HEIGHT, height)
            block(binder, bundle)
//            try {
//            } catch (exception: RemoteException) {
//                Log.w(TAG, "catch stash snapshot to service error", exception)
//            }
        }

        /**
         * 读取匿名共享内存
         * @param params
         * @param length 数据结果的长度
         * @return 数据结果，null表示读取失败
         */
        fun readFromMemoryAsByteArray(
            KEY_FD: String,
            params: HashMap<String?, ParcelFileDescriptor?>,
            length: Int
        ): ByteArray? {
            val fileDescriptor = params[KEY_FD]
            fileDescriptor?.apply {
                val data = ByteArray(length)
                var fileInputStream: FileInputStream? = null
                try {
                    fileInputStream = FileInputStream(fileDescriptor.fileDescriptor)
                    fileInputStream.read(data)
                    return data
                } catch (e: java.lang.Exception) {
                    Log.w(TAG, "catch read from memory error", e)
                } finally {
                    fileInputStream?.apply {
                        try {
                            fileDescriptor.close()
                        } catch (e: IOException) {
                            Log.w(TAG, "catch close fd error", e)
                        }
                    }
                }
            }
            return null
        }
    }
}