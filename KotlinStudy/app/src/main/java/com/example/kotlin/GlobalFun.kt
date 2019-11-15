package com.example.kotlin

/**
 * create by zuyuan on 2019/11/15
 */
fun appendString(tag: String, vararg otherInfo: Any?): String {
    val strBuilder: StringBuilder = StringBuilder(tag)
    for (info: Any? in otherInfo) {
        strBuilder.append(info?.toString() ?: "")
    }

    return strBuilder.toString()
}

tailrec fun tailrecFun(str: String) {
    println(str)
    tailrecFun(str)
}

fun test() {
    appendString("", null)

    val size = 10
    val strs: Array<String?> = arrayOfNulls(size)
    strs.swap(1, 2)
}

fun <T> Array<T>.swap(i: Int, j: Int): Boolean {
    if (i < 0 || j < 0 || i >= this.size || j >= this.size) return false
    if (i == j) return true

    val iT: T = this[i]
    this[i] = this[j]
    this[j] = iT

    return true
}