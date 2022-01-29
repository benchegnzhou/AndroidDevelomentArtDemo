package com.zbc.androiddevelomentartdemo.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zbc.androiddevelomentartdemo.R
import kotlin.concurrent.thread

class ThreadLocalTestActivity : AppCompatActivity() {
    private var threadLocal: ThreadLocal<Any> = ThreadLocal<Any>()
    private var threadLocal2: ThreadLocal<Any> = ThreadLocal<Any>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread_local_test)
        threadLocal.set("this is first string!")
        threadLocal.set("this is two string!")
        threadLocal2.set("this is three string!")
        threadLocal2.set("this is four string!")
        threadLocal.set(10)
        threadLocal.set(10.0f)
        Log.e("----", "创建线程获取到的值 threadLocal2 = ${threadLocal2.get()}");
        Log.e("----", "创建线程获取到的值 threadLocal = ${threadLocal.get()}");
        thread {
            threadLocal2.set("我是子线程设置的数据！！！")
            threadLocal.set("我是子线程设置的数据！！！")
            for (i in 1..4) {
                Log.e("----", "子线程获取到的 threadLocal2 = ${threadLocal2.get()}");
                Log.e("----", "子线程获取到的 threadLocal = ${threadLocal.get()}");
            }
        }
    }

}

