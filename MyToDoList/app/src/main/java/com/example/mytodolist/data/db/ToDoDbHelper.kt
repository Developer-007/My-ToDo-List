package com.example.mytodolist.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ToDoDbHelper(context: Context): SQLiteOpenHelper(context, ToDoDbNameClass.DATABASE_NAME, null, ToDoDbNameClass.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ToDoDbNameClass.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(ToDoDbNameClass.SQL_DELETE_TABLE)
        onCreate(db)
    }
}