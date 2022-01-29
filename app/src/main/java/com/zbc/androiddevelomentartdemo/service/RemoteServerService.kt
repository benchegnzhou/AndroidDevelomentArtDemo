package com.zbc.androiddevelomentartdemo.service

import android.app.Service
import android.content.Intent
import android.os.*
import com.zbc.androiddevelomentartdemo.activity.TransformDataServiceActivity
import com.ztsc.commonutils.logcat.Logger

/**
 * 模拟远程的service组件
 */
class RemoteServerService : Service() {
    companion object {
        private const val CODE_BASE = 0
        const val CODE_BINDER_REGISTER = CODE_BASE + 1
        const val CODE_CALL_METHOD = CODE_BASE + 2
    }

    private var mNotifyMessenger: Messenger? = null

    private val mHandler by lazy {
        object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    CODE_BINDER_REGISTER -> {
                        val reply = Message.obtain()
                        msg.data?.apply {
                            //如果本次通讯是跨进程的数据传递，一定要设置classLoader，否则会因找不到可用的classLoader而报错 Parcel: Class not found when unmarshalling: com.zbc.androiddevelomentartdemo.entity.SystemMsgBean
                            classLoader = javaClass.classLoader

                            get("binder")?.apply {
                                mNotifyMessenger = Messenger(this as IBinder)
                            }
                            Logger.e("Service接收到信息！${getString("data")}")
                        }

                        msg.replyTo.send(reply.apply {
                            what = TransformDataServiceActivity.CODE_REPLAY
                            data = Bundle().apply { putString("message", "binder服务双向注册成功！") }
                            //请不要向obj中存放数据，或crash 无法序列化
                            //obj ="xxxx"
                        })
                        reply.recycle()
                    }
                }
            }
        }
    }
    val mMessager = Messenger(mHandler)


    override fun onBind(intent: Intent?): IBinder? {
        return mMessager.binder
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }
}
