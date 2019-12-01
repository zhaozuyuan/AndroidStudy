package com.example.plugin_one

import com.example.plugin_common.Test

/**
 * create by zuyuan on 2019/11/22
 */
class T1 : Test() {
    init {
        println("------------>: common.Test")
    }

    /*简写方式: val 变量 = { 参数1: 参数类型1 -> 表达式 }*/
    val sum0 = { a: Int, b: Int -> a + b }

    /*完整方式: val 变量: (参数类型1) -> 返回类型 = { 参数1 -> 表达式 }*/
    val sum1: (Int, Int) -> Int = { a, b -> a + b }

    /*方法中必须使用完整方式，= 后面是默认值，一般是： 参数: (表达式参数类型) -> (表达式返回类型)*/
    fun test(name: String = "小明", sum : (Int, Int) -> (Int) = { a, b -> sum0(a, b) }) { }

    fun aFun() {
        val strBuilder = StringBuilder()
        val string: String = with(strBuilder) {
            buildString {  }
            append("hello, ")
            append("world! ")
            toString()
        }

        val string2: String = StringBuilder().apply {
            append("hello, ")
            append("world! ")
        }.toString()

        val str = buildString {
            append("1")
            append("2")
        }

        var i: Int? = null
        i?.let {
            it.toLong()
            println("i is not null!")
        }

    }

    fun compare(value: String) = value.length


}