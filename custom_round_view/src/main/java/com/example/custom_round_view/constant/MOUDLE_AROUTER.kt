package com.example.custom_round_view.constant

/**
 * 页面导航
 */
open class MOUDLE_AROUTER {
    companion object{
        const val ROUNDVIEW_MAIN_ACTIVITY= "/${PATH_CONSTANT.MODULE_ROUTER}/RoundViewMainActivity"
        const val ROUNDVIEW_MAIN_ACTIVITY2= "/custom_round_view/RoundViewMainActivity"


        fun getPath():String{
            return ROUNDVIEW_MAIN_ACTIVITY
        }
    }
}