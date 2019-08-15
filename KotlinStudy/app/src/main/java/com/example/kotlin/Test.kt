package com.example.kotlin

import java.lang.RuntimeException
import java.lang.StringBuilder

/**
 * create by zuyuan on 2019/5/16
 */
fun main(arg: Array<String> ) {
    for (i in 0..10) print("$i ")
    println()

    //不包含10
    for (i in 0 until 10) print("$i ")
    println()

    //递增2
    for (i in 0..10 step  2) print("$i ")
    println()

    for (i in 10 downTo 0 step 2) print("$i ")
    println()

    var i = 1
    out@ for (j in 0..100)
        for (t in 0..100) {
            i = i + t
            if (i >= 50) break@out
        }
    println("--->\"${i}\"")

    when(i) {
        0 -> throw RuntimeException("--->")
        1, 2 -> println("--->")
        else -> println("--->$i")
    }

    var str: String ?= null
    str.isNullOrEmpty()

    val s1 = "sss"
    val s2 = "sss"
    val s3 : String = StringBuilder("sss").toString()

    println(s1 === s2)
    println(s1 == s2)
    println(s1 === s3)


}