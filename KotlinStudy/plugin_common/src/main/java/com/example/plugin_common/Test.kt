package com.example.plugin_common

/**
 * create by zuyuan on 2019/11/21
 */
open class Test {

    inline fun <reified T: Any> test(c: Char, result: (Char) -> Boolean = { false }) : T? {
        println("result = ${result(c)}")
        return T::class.java.newInstance()
    }

    fun start() {
        val string: String? = test('a'){ it == 'a'}

        compute{ a, b -> a * b}
    }

    val add = {a: Int, b: Int -> a + b}

    // 修饰符: (参数类型) -> 返回类型 = {参数1.. -> 运算 }
    val add2: (Int, Int) -> Int = { a, b -> a + b }

    /* 方法中的表达式必须显示表达！*/
    inline fun compute(function: (Int, Int) -> Int = { a, b -> add(a, b) }) {
        val a = 1
        val b = 2
        function(a, b)
    }
}