package com.zbc.androiddevelomentartdemo

import android.app.Activity
import com.example.kotlincoroutinedemo.KotlinCoroutineHomeList
import com.zbc.androiddevelomentartdemo.activity.*

class HomeDataHelper {
    companion object {
        private const val BASE_NUM = 0
        val HOME_DATA_LIST = mutableListOf<HomeBean>(
            HomeBean("数据序列化", DemoTestActivity::class.java, BASE_NUM + 1, "数据序列化"),
            HomeBean(
                "service为载体，通过binder服务双向传递数据",
                PassingDataServiceActivity::class.java,
                1,
                "service为载体，通过binder服务双向传递数据"
            ),
            HomeBean(
                "android且套滑动事件冲突解决",
                ScrollConflictActivity::class.java,
                BASE_NUM + 2,
                "android且套滑动事件冲突解决"
            ),
            HomeBean(
                "发送控制命令给Service",
                TransformDataServiceActivity::class.java,
                BASE_NUM + 3,
                "发送控制命令给Service"
            ),
            HomeBean(
                "IBinder数据传递",
                ScrollConflictActivity::class.java,
                BASE_NUM + 4,
                "IBinder数据传递"
            ),
            HomeBean("手势监听识别", GuestureDelectorActivity::class.java, BASE_NUM + 5, "手势监听识别s"),
            HomeBean(
                "remoteViewDemo",
                RemoteViewDemoActivity::class.java,
                BASE_NUM + 6,
                "remoteViewDemo"
            ),
            HomeBean("桌面图标小红点", BadgeDemoActivity::class.java, BASE_NUM + 7, "桌面图标小红点"),
            HomeBean(
                "drawable的淡入淡出",
                DrawableTestActivity::class.java,
                BASE_NUM + 8,
                "drawable的淡入淡出"
            ),
            HomeBean(
                "android 动画演示",
                DemoAnimationActivity::class.java,
                BASE_NUM + 9,
                "android 动画演示"
            ),
            HomeBean(
                "android 控件自定义基础",
                CustomViewActivity::class.java,
                BASE_NUM + 10,
                "android 控件自定义基础"
            ),
            HomeBean(
                "android 高级自定义控件",
                CustomViewActivity::class.java,
                BASE_NUM + 11,
                "android 高级自定义控件"
            ),
            HomeBean(
                "android compose",
                CustomViewActivity::class.java,
                BASE_NUM + 12,
                "android compose"
            ),
            HomeBean(
                "android live data",
                CustomViewActivity::class.java,
                BASE_NUM + 13,
                "android live data"
            ),
            HomeBean(
                "Kotlin Coroutine",
                KotlinCoroutineHomeList::class.java,
                14,
                "Kotlin Coroutine"
            ),
            HomeBean(
                "ThreadLocal Test",
                ThreadLocalTestActivity::class.java,
                15,
                "ThreadLocal Test"
            ),
        )
    }
//    ARouter.getInstance().build(MOUDLE_AROUTER.ROUNDVIEW_MAIN_ACTIVITY).navigation();

    data class HomeBean(
        var title: String?,
        val clazz: Class<out Activity>,
        var position: Int,
        val description: String?
    )
}