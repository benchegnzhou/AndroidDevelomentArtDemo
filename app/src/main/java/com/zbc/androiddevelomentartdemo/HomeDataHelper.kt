package com.zbc.androiddevelomentartdemo

import android.app.Activity
import com.example.kotlincoroutinedemo.KotlinCoroutineHomeList
import com.zbc.androiddevelomentartdemo.activity.*

class HomeDataHelper {
    companion object {

        val HOME_DATA_LIST = mutableListOf<HomeBean>(
            HomeBean("数据序列化", DemoTestActivity::class.java, 0, "数据序列化"),
            HomeBean("传递信息给指定的service", CustomViewActivity::class.java, 1, "传递信息给指定的service"),
            HomeBean(
                "android且套滑动事件冲突解决",
                ScrollConflictActivity::class.java,
                2,
                "android且套滑动事件冲突解决"
            ),
            HomeBean("IBinder数据传递", ScrollConflictActivity::class.java, 3, "IBinder数据传递"),
            HomeBean("手势监听识别", GuestureDelectorActivity::class.java, 4, "手势监听识别s"),
            HomeBean("remoteViewDemo", RemoteViewDemoActivity::class.java, 5, "remoteViewDemo"),
            HomeBean("桌面图标小红点", BadgeDemoActivity::class.java, 6, "桌面图标小红点"),
            HomeBean("drawable的淡入淡出", DrawableTestActivity::class.java, 7, "drawable的淡入淡出"),
            HomeBean("android 动画演示", DemoAnimationActivity::class.java, 8, "android 动画演示"),
            HomeBean("android 控件自定义基础", CustomViewActivity::class.java, 9, "android 控件自定义基础"),
            HomeBean("android 高级自定义控件", CustomViewActivity::class.java, 10, "android 高级自定义控件"),
            HomeBean("android compose", CustomViewActivity::class.java, 11, "android compose"),
            HomeBean("android live data", CustomViewActivity::class.java, 12, "android live data"),
            HomeBean("Kotlin Coroutine", KotlinCoroutineHomeList::class.java, 13, "Kotlin Coroutine"),
            HomeBean("ThreadLocal Test", ThreadLocalTestActivity::class.java, 14, "ThreadLocal Test"),
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