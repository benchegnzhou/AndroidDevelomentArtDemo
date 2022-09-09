package com.zbc.gymappointment

import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_gym_appointment.*
import java.text.SimpleDateFormat
import java.util.*

@Route(path = "/gym/GymAppointmentActivity")
class GymAppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_gym_appointment)
        actionBar?.hide()
        actionBarTran()
        fill_data()
    }

    fun fill_data() {
        val currentTime = Date()
        val formatter = SimpleDateFormat("MM.dd")
        val dateString: String = formatter.format(currentTime)

        val spanString:SpannableString =  SpannableString(dateString)
        val span = AbsoluteSizeSpan(44,true)
        spanString.setSpan(span, 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        tv_date.text = spanString
        tv_week.text = getWeekOfDate(currentTime)?.replace("星期","周")
    }

    /**
     * 获取当前日期是星期几<br></br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    fun getWeekOfDate(date: Date?): String? {
        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val cal = Calendar.getInstance()
        cal.time = date
        var w = cal[Calendar.DAY_OF_WEEK] - 1
        if (w < 0) w = 0
        return weekDays[w]
    }

    fun actionBarTran() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
        //状态栏字体变黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}