package com.example.kotlin

import android.os.SystemClock

/**
 * create by zuyuan on 2019/11/28
 */
suspend fun print1() : String {
    delay(1000)
    return "print1"
}

suspend fun print2() : String {
    delay(1000)
    return "print2"
}

fun delay(duration: Long) {
    SystemClock.sleep(duration)
}

fun main() {
//    val s1 = print1()
//    val s2 = print2()
}
