package com.zbc.androiddevelomentartdemo.content

interface ContentValue {
    companion object {
        const val MSG_FROM_CLIENT = 130
        const val MSG_FROM_SERVICE = 200


        const val KEY_FD = "com.zbc.androiddevelomentartdemo.client"
        const val KEY_LENGTH = "${KEY_FD}_KEY_LENGTH"
        const val KEY_WIDTH = "${KEY_FD}_KEY_WIDTH"
        const val KEY_HEIGHT = "${KEY_FD}_KEY_HEIGHT"
    }
}