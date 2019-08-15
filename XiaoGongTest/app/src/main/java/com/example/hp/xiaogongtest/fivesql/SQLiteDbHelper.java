package com.example.hp.xiaogongtest.fivesql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @类名:SQLiteDbHelper
 * @创建人:赵祖元
 * @创建时间：2018/8/14 16:13
 * @简述:
 */
public class SQLiteDbHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 2;                 //版本

    public static final String DB_NAME = "database.db";     //数据库名字

    public static final String TABLE_STUDENT = "students";     //表名

    public static final String STUDENT_CREATE_TABLE_SQL = "create table "
            + TABLE_STUDENT +"("
            + "id integer PRIMARY KEY AUTOINCREMENT,"
            + "name vachar(20) not null,"
            + "tel_no vacher(11) NOT NULL,"
            + "cls_id integer NOT NULL"
            + ");";

    public SQLiteDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STUDENT_CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
                upgradeFromVersion2(db);
                break;
        }
    }

    private void upgradeFromVersion2(SQLiteDatabase db){
        db.execSQL(" alter table " + TABLE_STUDENT + " add column age integer");
    }
}
