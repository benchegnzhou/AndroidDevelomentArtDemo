package com.zbc.androiddevelomentartdemo.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.zbc.androiddevelomentartdemo.R
import com.zbc.androiddevelomentartdemo.content.ActRouter
import com.zbc.androiddevelomentartdemo.service.DumpTestService
import kotlinx.android.synthetic.main.activity_dump_test.*
import java.io.FileDescriptor
import java.io.PrintWriter
import java.lang.ref.WeakReference

@Route(path = ActRouter.DUMP_SERVICE_TEST_ACTIVITY)
class DumpServiceTestActivity : AppCompatActivity() {
    private var binder: DumpTestService.DumpBinder? = null
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = if (service == null) null else (service as DumpTestService.DumpBinder)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }
    private var mBinder: DBinder? = null


    class DBinder : Binder {
        lateinit var weakReference: WeakReference<TextView>
        lateinit var weakActivity: WeakReference<AppCompatActivity>

        constructor(textView: TextView, activity: AppCompatActivity) {
            weakReference = WeakReference(textView)
            weakActivity = WeakReference(activity)
        }

        fun sendMessage(msg: String?) {
            weakActivity?.get()?.runOnUiThread {
                weakReference?.get()?.text = msg
                Log.e("log",msg!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dump_test)
        mBinder = DBinder(tv_msg, DumpServiceTestActivity@ this)
        val intent = Intent(DumpServiceTestActivity@ this, DumpTestService::class.java)
        intent.putExtras(Bundle().also { it.putBinder("mBinder", mBinder) })
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }


    override fun dump(
        prefix: String,
        fd: FileDescriptor?,
        writer: PrintWriter,
        args: Array<out String>?
    ) {
        writer.println("come from DumpServiceTestActivity\nTest dump message")
    }

}