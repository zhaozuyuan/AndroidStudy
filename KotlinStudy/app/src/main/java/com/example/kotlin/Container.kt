package com.example.kotlin

/**
 * create by zuyuan on 2019/5/16
 */

/**
 * set 集合是随机存储的，不能使用下标访问，不允许修改内部元素的值
 */
fun set() {
    val data: Set<Int> = setOf(1, 2, 3)

    for (i: Int in data) {
        i % 10
    }
}

fun list() {
    //这些地方应该显示地申明
    val data : List<String> = arrayListOf("小明", "小赵")

    for (i in data.indices) {
        data[i].substring(0)
    }

    //隐含it指代每一个元素
    data.forEach { it.substring(0) }

    //降序排列 默认是it指代参数
    data.sortedByDescending { it.length }
    data.sortedByDescending { s -> s.length}

}

fun map() {
    val data : Map<String, Int> = hashMapOf("一" to 1, "二" to 2)

    //返回一个Map.Entry
    data.forEach { it.key.substring(it.value) }

    for (i : Map.Entry<String, Int> in data) {
        i.key.substring(i.value)
    }

    //迭代器
    val iterator: Iterator<Map.Entry<String, Int>> = data.iterator()
    while (iterator.hasNext()) {
        val entry = iterator.next()
        entry.key.substring(entry.value)
    }
}