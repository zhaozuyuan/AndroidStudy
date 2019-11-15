package com.example.kotlin

import android.util.ArrayMap
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/**
 * create by zuyuan on 2019/11/14
 */
public class Test {
    val str = "小明"
    var varStr = "小红"
    val value = 123
    val valueObj: Int = 123456

    fun changeName() = run { varStr = str }

    fun aVoid(str: String) = Log.d("TAG", str)

    fun createMapWithValue(): Map<String, Int> {
        return mapOf(Pair("a", value))
    }

    fun test() {
        val strArray: Array<String?> = arrayOfNulls(6)
        strArray[0] = "123"

        Log.d("TAG", "美元\$${strArray[0]}")

        strArray.forEach { println(it) }

        val strList: ArrayList<String?> = arrayListOf("你好")
        strList.addAll(strArray)

        val strMutable: MutableList<String?> = ArrayList()
        strMutable.addAll(strArray)

        val strMap: MutableMap<Int, String> = TreeMap()
        strMap[1] = "123"

        var result: String = if (strArray[0] == "123") {
            println("compare")
            "456"
        } else {
            "789"
        }

        result = when (strArray[0]) {
                "123"-> "456${strArray[0]}"
                else -> "789"
            }

        val list: List<String?> = listOf()
        if (list is ArrayList) {
            list.add("1")
        }

        list as ArrayList
        list.add("1")

        val judge: Boolean = list[0].isNullOrEmpty()
        val judge2: Boolean = if (list[0].isNullOrEmpty()) false else list[0]!!.isBlank()

        val i: Int = getID("name", "as", like = "篮球")
    }

    private fun getID(name: String,  vararg args: String?, age: Int = -1, like: String = ""): Int = 123

    private fun <T> genericFun(): T? = null

    private fun transfer() {
        inlineFun("")
    }

    private inline fun inlineFun(str: String) {
        println(str)
        printfData(str)
    }

    private fun printfData(str: String) {
        println(str)
    }

    private fun templateFun(a: Int, b: Int) = a >= b

    private fun transferTemplateFun(compare: (a: Int, b: Int) -> Boolean) {
        compare(1, 2)
    }

    private fun tansferStart() {
        transferTemplateFun(this::templateFun)
        transferTemplateFun({a, b -> templateFun(a, b)})
        transferTemplateFun{a, b -> templateFun(a, b)}

    }
}