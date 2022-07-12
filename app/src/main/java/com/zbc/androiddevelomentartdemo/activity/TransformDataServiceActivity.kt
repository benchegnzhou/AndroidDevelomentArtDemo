package com.zbc.androiddevelomentartdemo.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.zbc.androiddevelomentartdemo.R
import com.zbc.androiddevelomentartdemo.entity.SystemMsgBean
import com.zbc.androiddevelomentartdemo.entity.UserBean
import com.zbc.androiddevelomentartdemo.service.RemoteServerService
import com.ztsc.commonutils.logcat.Logger
import kotlinx.android.synthetic.main.activity_transform_data_to_service.*

/**
 * bidner  简单完成传递数据给Service
 */
class TransformDataServiceActivity : AppCompatActivity() {
    companion object {
        private const val CODE_BASE = 0
        const val CODE_REPLAY = CODE_BASE + 1
        private var mServiceMessenger: Messenger? = null
    }

    private var isBindServiceSuccess = false

    private val mHandler =
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    CODE_REPLAY -> {
                        service_message.text ="${msg.data?.getString("message")}"
                    }
                }
            }
        }
    private val mMessager = Messenger(mHandler)


    private val mServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mServiceMessenger = Messenger(service)
                service_message.text = "服务绑定成功！"
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transform_data_to_service)
        bindService(
            Intent().apply {
                setAction("service.RemoteServerService")
                setPackage(this@TransformDataServiceActivity.packageName)
            },
            mServiceConnection,
            Context.BIND_AUTO_CREATE
        )
        btn_act_send_data.setOnClickListener {
            val mess = Message.obtain()
            mServiceMessenger?.send(mess.apply {
                what = RemoteServerService.CODE_BINDER_REGISTER
                data = Bundle().apply {
                    putString("data","注册binder")
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        putBinder("binder", mMessager.binder)
                    }
                }

                replyTo = mMessager
            })
            mess.recycle()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBindServiceSuccess) {
            mServiceMessenger = null
        }
    }

}