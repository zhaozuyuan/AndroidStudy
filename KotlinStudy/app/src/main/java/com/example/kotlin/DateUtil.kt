package com.example.kotlin

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * create by zuyuan on 2019/11/15
 */
object DateUtil {
    val nowDateTime: String
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(Date())

    val nowDateTimeCutBigTime: String
        get() {
            val nowTime: String = nowDateTime
            return nowTime.substring(11, nowTime.length)
        }
}