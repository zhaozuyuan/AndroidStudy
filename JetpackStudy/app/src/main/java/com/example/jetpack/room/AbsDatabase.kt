package com.example.jetpack.room

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration

/**
 * create by zuyuan on 2019/11/25
 */

//@TypeConverters 可指定其它的转换器
@Database(entities = [PersonEntity::class], version = 1)
abstract class AbsDatabase : RoomDatabase() {

    abstract fun peopleDao(): PeopleDao
}

/* 单例获取 */
class AppDatabase {
    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var instance: AbsDatabase? = null

        private val LOCK = Any()

        private const val DATABASE_NAME = "jet_pack.db"

        fun getInstance(context: Context) : AbsDatabase {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                                AbsDatabase::class.java, DATABASE_NAME)
                                .allowMainThreadQueries()
                                .build()
                    }
                }
            }
            return instance!!
        }

        /* 修改数据库 */
        fun updateDatabase(context: Context, migration: Migration) {
            if (instance == null) {
                synchronized(LOCK) {
                    //如果正在初始化，那么应该再判断一次
                    if (instance == null) {
                        getInstance(context)
                    }
                }
            }

            synchronized(LOCK) {
                //升级数据库
                this.instance = Room.databaseBuilder(context.applicationContext,
                        AbsDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .addMigrations(migration)
                        .build()
            }
        }
    }
}