package com.zbc.gymappointment

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.zbc.gymappointment.test.*
import kotlinx.android.synthetic.main.activity_anim_test.*

@Route(path = "/gym/AnimDemoActivity")
class AnimDemoActivity : AppCompatActivity() {
    private val TAG = "AnimDemoActivity"
    var kass = KClass("kClazz")

    private var startTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_test)

//        container_1.addView(CustomImageView(context = this).apply { setBackgroundColor(Color.RED) })
//        container_1.addView(CustomImageView(context = this))
        view_root.clipChildren = false

        custom_view.tag = kass
        custom_view_2.tag = kass

        view_root.setOnClickListener { _ ->
            TestClass().test()
            startActivity(Intent(this@AnimDemoActivity, TestActivity::class.java))
            for (i in 0..10) {

            }
            startTime = System.currentTimeMillis()
            val contentDescription: String = getString(R.string.screen_number, 1)
            Log.e(TAG, "$contentDescription , time = ${System.currentTimeMillis() - startTime}")

//            ValueAnimator().apply {
//                setFloatValues(0f, 500f)
//                duration = 2000
//                addUpdateListener (object : ValueAnimator.AnimatorUpdateListener {
//                    override fun onAnimationUpdate(animation: ValueAnimator?) {
//                        val v = animation?.animatedValue
//                        container_1.getChildAt(0).apply {
//                            translationX= v as Float
//                            Log.e("-----","value = ${v}")
//                        }
//                    }
//                })
//                }.start()
            Log.e(
                "-------",
                "findViewWithTag   ${
                    view_root.findViewWithTag<View>(kass).hashCode()
                }  ,  custom_view_2.hashcode = ${custom_view_2.hashCode()}"
            )
            val animator: ObjectAnimator =
                ObjectAnimator.ofFloat(container_1.getChildAt(0), "y", 0f, 800f)
            animator.setDuration(5000)
            animator.start()
            if (container_1.getChildAt(0) is IAnim) {
                container_1.getChildAt(0).let {
                    Log.e("----", "${it.javaClass.simpleName} , ${it.isAttachedToWindow}")
                }
            }
        }
    }
//        _ ->
//            {
////                ValueAnimator().values()(Animation.ABSOLUTE,0f,500f,Animation.ABSOLUTE,0f,0f).
//                val anim = TranslateAnimation(Animation.ABSOLUTE,0f,Animation.ABSOLUTE,2f,Animation.ABSOLUTE,0f,Animation.ABSOLUTE,0f)
//                anim.duration = 1000
//                anim.repeatMode = TranslateAnimation.REVERSE
//                container_1.getChildAt(0).animation =anim
//                anim.start()
//            }

}