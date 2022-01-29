package com.zbc.androiddevelomentartdemo.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.zbc.androiddevelomentartdemo.R
import com.zbc.androiddevelomentartdemo.entity.SystemMsgBean
import com.zbc.androiddevelomentartdemo.helper.BinderProxy
import com.zbc.androiddevelomentartdemo.service.IServiceNotify
import com.zbc.androiddevelomentartdemo.service.MessageServerService
import com.ztsc.commonutils.bitmap.BitmapUtils
import com.ztsc.commonutils.logcat.Logger
import kotlinx.android.synthetic.main.activity_passing_data_service.*
import java.io.InputStream

/**
 * Activity与指定的Service互传数据
 */
class PassingDataServiceActivity : AppCompatActivity() {
    private var mIBinder: IBinder? = null

    private var mIsConnectionBind = false
    private var mBinderProxy: BinderProxy? = null
    private val mNotifyBinder: IBinder by lazy {
        object : IServiceNotify.Proxy() {
            override fun registerService() {

            }

            override fun unRegisterService() {

            }

            override fun callServiceMethod() {

            }

            override fun sendMessage(message: String?) {
                runOnUiThread {
                    service_message.text = "客户端服务接受到发送过来的数据 $message"
                }
            }

            override fun transmitMemoryFile(byteArray: ByteArray?) {
                runOnUiThread {
                    val bytes2Drawable = BitmapUtils.bytes2Drawable(byteArray)
                    bytes2Drawable?.apply {
                        val layoutParams = iv_image.layoutParams
                        val intrinsicWidth = bytes2Drawable.intrinsicWidth
                        layoutParams.height = (iv_image.width *1.0f *bytes2Drawable.intrinsicHeight/intrinsicWidth).toInt()
                        iv_image.layoutParams = layoutParams
                        iv_image.setImageDrawable(bytes2Drawable)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passing_data_service)
        connectService()
        btn_act_send_data.setOnClickListener {
            connectService()
            mBinderProxy?.sendMessage("通过binder发送数据到另一个进程")
        }
        btn_act_call_method1.setOnClickListener {
            connectService()
            mBinderProxy?.method1("通过binder发送数据到另一个进程,调用约定好的method1")
        }
        btn_act_call_method2.setOnClickListener {
            connectService()
            mBinderProxy?.method2(10, 20)
        }
        btn_act_call_request_memory_file.setOnClickListener {
            connectService()
            mBinderProxy?.requestMemoryFile()
        }
    }


    private fun connectService() {
        if (!mIsConnectionBind || mIBinder == null || !mIBinder!!.isBinderAlive) {
            bindService()
        }
    }

    /**
     * 绑定数据服务
     */
    private fun bindService() {
        val intent = Intent(
            this, MessageServerService::class.java
        )
        intent.putExtras(Bundle()
            .apply {
                putParcelable(
                    "systemMsgBean",
                    SystemMsgBean(
                        "a00001",
                        "Hello ! 我是通过bundle传递数据",
                        System.currentTimeMillis(),
                        0
                    )
                )
            })
        mIsConnectionBind = bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        service_message.text = "服务绑定成功！等待传输数据~"
    }


    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Logger.e("the ComponentName is :" + name?.className)
            mIBinder = service
            mBinderProxy = BinderProxy(mIBinder)
            mBinderProxy?.registerNotifyBinder(mNotifyBinder)
            liveData()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mIBinder = null
            mIsConnectionBind = false
        }
    }

    fun liveData() {
        // Create the observer which updates the UI.
        val nameObserver = Observer<String> { newName ->
            // Update the UI, in this case, a TextView.
            service_message.text = newName
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        mBinderProxy?.mCurrentName?.observe(this, nameObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mIsConnectionBind) {
            mBinderProxy?.unRegisterService()
            mBinderProxy?.release()
            mBinderProxy == null
            unbindService(mConnection)
        }
    }
}