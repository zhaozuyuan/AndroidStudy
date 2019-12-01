package com.example.jetpack.room

import androidx.room.*
import io.reactivex.Single

/**
 * create by zuyuan on 2019/11/25
 */
@Dao interface PeopleDao {

    /* 插入一个或多个Person，冲突则替代旧数据 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersons(vararg personEntity: PersonEntity)

    /* 更新一个或多个Person，冲突则替代旧数据 */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePersons(vararg personEntity: PersonEntity)

    /* 删除一个或多个人 */
    @Delete
    fun deletePersons(vararg personEntity: PersonEntity)

    /* 查询所有的Person */
    @Query("SELECT * FROM people")
    fun queryAllPerson() : Array<PersonEntity>

    /* 根据学号查询Person */
    @Query("SELECT * FROM people WHERE id == :id")
    fun queryPerson(id: Int) : Single<PersonEntity>
}