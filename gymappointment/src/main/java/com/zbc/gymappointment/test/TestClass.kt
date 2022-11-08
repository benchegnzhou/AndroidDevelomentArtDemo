package com.zbc.gymappointment.test

import android.util.Log
import java.util.concurrent.locks.ReentrantLock

/**
 * 这个示例可以完成同步锁实验和java阻塞队列实验，
 * 如果您有兴趣可以参考https://www.cnblogs.com/teach/p/10665199.html
 */
class TestClass {
    val TAG = TestClass::class.java.simpleName
    val items = arrayOfNulls<String?>(10)
    val maxCount = 10
    val lock = ReentrantLock(false);


    var putIndex = 0
    val notEmpty = lock.newCondition()
    val notFull = lock.newCondition()

    fun test() {
        val thread1 = object : Thread("Thread A") {
            override fun run() {
                Log.e(TAG, "thread1 开始运行")
                while (true) {
                    lock.lockInterruptibly()
                    if (putIndex >= maxCount) {
                        Log.e(TAG, "thread1 等待中")
                        notFull.await()
                    }
                    items[putIndex] = "添加的第${putIndex}个元素"
                    Log.e(TAG, "添加元素 ${items[putIndex]}")
                    putIndex++
                    notEmpty.signal()
                    lock.unlock()
                    sleep(1000)
                }
            }
        }


        val thread2 = object : Thread("Thread B") {
            override fun run() {
                Log.e(TAG, "thread2 开始运行")
                while (true) {
                    lock.lockInterruptibly()
                    if (putIndex <= 0) {
                        Log.e(TAG, "thread2 进入等待")
                        notEmpty.await()
                    }
                    putIndex--
                    Log.e(TAG, "去除元素 ${items[putIndex]}")
                    items[putIndex] = null
                    notFull.signal()
                    lock.unlock()
                    sleep(2000)
                }
            }
        }

        thread2.start()
        thread1.start()
    }

}