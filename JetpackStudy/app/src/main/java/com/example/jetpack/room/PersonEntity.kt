package com.example.jetpack.room

import androidx.room.Entity
import androidx.room.Index

/**
 * create by zuyuan on 2019/11/25
 */

/*
  表名: people
  主键: id
  索引: age

  字段: id: 学号
       name: 姓名
       age: 年龄
       like: 爱好
 */
@Entity(tableName = "people",
        primaryKeys = ["id"],
        indices = [Index("age", "id")]
)
data class PersonEntity(var id: Int,
                        var name: String,
                        var age: Int,
                        var like: String)