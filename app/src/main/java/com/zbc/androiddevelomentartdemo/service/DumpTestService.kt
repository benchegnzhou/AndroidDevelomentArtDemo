package com.zbc.androiddevelomentartdemo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.zbc.androiddevelomentartdemo.activity.DumpServiceTestActivity
import java.io.FileDescriptor
import java.io.PrintWriter

/**
 * 文章看考
 * https://blog.csdn.net/u013082948/article/details/102498113?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-5-102498113-blog-54091917.pc_relevant_aa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-5-102498113-blog-54091917.pc_relevant_aa&utm_relevant_index=8
 * 测试 Dump
 */
class DumpTestService : Service() {
    class DumpBinder : Binder() {
        fun sendMessage(msg: String?) {

        }
    }

    val massage = DumpBinder()
    var clientBinder: DumpServiceTestActivity.DBinder? = null
    override fun onBind(intent: Intent?): IBinder? {
        clientBinder = intent?.extras?.getBinder("mBinder") as DumpServiceTestActivity.DBinder?
        clientBinder?.sendMessage("重写dump函数，通过Activity绑定，然后运行APP,启动当前service\n" +
                "通过执行adb shell → dumpsys activity service {当前service名称}\n\n" +
                "运行结果\n" +
                "ares:/ \$ dumpsys activity service DumpTestService\n" +
                "SERVICE com.zbc.androiddevelomentartdemo/.service.DumpTestService 55f77a7 pid=4015 user=0\n" +
                "Client:\n" +
                "come from DumpTestService\n" +
                "Test dump message")
        return massage
    }

    /**
     * 重写这个函数，通过Activity绑定，然后运行APP,启动当前service
     * 通过执行adb shell → dumpsys activity service {当前service名称}
     *
     * 运行结果
     *
     * ares:/ $ dumpsys activity service DumpTestService
     *
     * SERVICE com.zbc.androiddevelomentartdemo/.service.DumpTestService 55f77a7 pid=4015 user=0
     * Client:
     * come from DumpTestService
     * Test dump message
     */
    override fun dump(fd: FileDescriptor?, writer: PrintWriter?, args: Array<out String>?) {
        writer?.println("come from DumpTestService\nTest dump message")
    }

}