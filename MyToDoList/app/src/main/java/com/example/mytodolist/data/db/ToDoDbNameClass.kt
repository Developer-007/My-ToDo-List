package com.example.mytodolist.data.db

import android.provider.BaseColumns

object ToDoDbNameClass {
    const val TABLE_NAME = "my_todo_table"
    const val COLUMN_NAME_TITLE="title"
    const val COLUMN_NAME_TIME="time"
    const val COLUMN_NAME_DESC="desc"

    const val DATABASE_VERSION=2
    const val DATABASE_NAME="MyToDoDb.db"

    const val CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT, $COLUMN_NAME_DESC TEXT, $COLUMN_NAME_TIME TEXT)"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}