package com.zbc.androiddevelomentartdemo

import android.app.Activity
import com.example.activityTransitiondemo.activity.ActivityTransitionDemoListActivity
import com.example.custom_round_view.RoundViewMainActivity
import com.example.custom_round_view.constant.MOUDLE_AROUTER
import com.example.kotlincoroutinedemo.KotlinCoroutineHomeList
import com.example.kotlincoroutinedemo.constant.RouterPath
import com.zbc.androiddevelomentartdemo.activity.*
import com.zbc.androiddevelomentartdemo.content.ActRouter

class HomeDataHelper {
    companion object {
        private const val BASE_NUM = 0
        val HOME_DATA_LIST = mutableListOf<HomeBean>(
            HomeBean("数据序列化", DemoTestActivity::class.java, null, null, BASE_NUM + 1, "数据序列化"),
            HomeBean(
                "service为载体，通过binder服务双向传递数据",
                PassingDataServiceActivity::class.java,
                null,
                null,
                1,
                "service为载体，通过binder服务双向传递数据"
            ),
            HomeBean(
                "android且套滑动事件冲突解决",
                ScrollConflictActivity::class.java,
                null,
                null,
                BASE_NUM + 2,
                "android且套滑动事件冲突解决"
            ),
            HomeBean(
                "发送控制命令给Service",
                TransformDataServiceActivity::class.java,
                null,
                null,
                BASE_NUM + 3,
                "发送控制命令给Service"
            ),
            HomeBean(
                "IBinder数据传递",
                ScrollConflictActivity::class.java,
                null,
                null,
                BASE_NUM + 4,
                "IBinder数据传递"
            ),
            HomeBean(
                "手势监听识别", GuestureDelectorActivity::class.java, null, null, BASE_NUM + 5, "手势监听识别s"
            ),
            HomeBean(
                "remoteViewDemo",
                RemoteViewDemoActivity::class.java,
                null,
                null,
                BASE_NUM + 6,
                "remoteViewDemo"
            ),
            HomeBean("桌面图标小红点", BadgeDemoActivity::class.java, null, null, BASE_NUM + 7, "桌面图标小红点"),
            HomeBean(
                "drawable的淡入淡出",
                DrawableTestActivity::class.java,
                null,
                null,
                BASE_NUM + 8,
                "drawable的淡入淡出"
            ),
            HomeBean(
                "android 动画演示",
                DemoAnimationActivity::class.java,
                null,
                null,
                BASE_NUM + 9,
                "android 动画演示"
            ),
            HomeBean(
                "android 控件自定义基础",
                null,
                ActRouter.CUSTOM_VIEW_ACTIVITY,
                null,
                BASE_NUM + 10,
                "android 控件自定义基础"
            ),
            HomeBean(
                "android 高级自定义控件",
                null,
                MOUDLE_AROUTER.ROUNDVIEW_MAIN_ACTIVITY,
                null,
                BASE_NUM + 11,
                "android 高级自定义控件"
            ),
            HomeBean(
                "android compose",
                CustomViewActivity::class.java,
                null,
                null,
                BASE_NUM + 12,
                "android compose"
            ),
            HomeBean(
                "android live data",
                CustomViewActivity::class.java,
                null,
                null,
                BASE_NUM + 13,
                "android live data"
            ),
            HomeBean(
                "Kotlin Coroutine",
                KotlinCoroutineHomeList::class.java,
                null,
                null,
                14,
                "Kotlin Coroutine"
            ),
            HomeBean(
                "ThreadLocal Test",
                ThreadLocalTestActivity::class.java,
                null,
                null,
                15,
                "ThreadLocal Test"
            ),
            HomeBean(
                "转场动画",
                null,
                com.example.activityTransitiondemo.contantvalue.MOUDLE_AROUTER.ACTIVITY_TRANSITION_DEMO_LIST_ACTIVITY,
                null,
                16,
                "转场动画"
            ),
            HomeBean(
                "dump测试",
                null,
                ActRouter.DUMP_SERVICE_TEST_ACTIVITY,
                null,
                17,
                "dump测试"
            ),
            HomeBean(
                "GymAppointment",
                null,
                ActRouter.GYM_APPOINTMENT_ACTIVITY,
                null,
                18,
                "GymAppointment"
            ),
            HomeBean(
                "AnimDemoActivity",
                null,
                ActRouter.GYM_ANIMDEMO_ACTIVITY,
                null,
                19,
                "AnimDemoActivity"
            ),
            HomeBean(
                "kotlinDemo",
                null,
                RouterPath.KOTLIN_COROUTINE_HOME_LIST_AT,
                null,
                19,
                "kotlinDemo"
            ),
        )
    }

    data class HomeBean(
        var title: String?,
        val clz: Class<out Activity>?,
        val activityPath: String?,
        val data: HashMap<String, Any>?,
        var position: Int,
        val description: String?
    )
}