package com.example.mytodolist.data.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.mytodolist.data.db.ToDoDbHelper
import com.example.mytodolist.data.db.ToDoDbNameClass
import com.example.mytodolist.data.model.TaskItem
import com.example.mytodolist.domain.repository.DbRepository

class DbRepositoryImpl(context: Context): DbRepository {
    val toDoDbHelper= ToDoDbHelper(context)
    var db: SQLiteDatabase? = null

    override fun openDb() {
        db=toDoDbHelper.writableDatabase
    }

    override fun closeDb() {
        toDoDbHelper.close()
    }

    override suspend fun insertToDb(title: String, desc: String, time: String) {
        val values= ContentValues().apply{
            put(ToDoDbNameClass.COLUMN_NAME_TITLE, title)
            put(ToDoDbNameClass.COLUMN_NAME_DESC, desc)
            put(ToDoDbNameClass.COLUMN_NAME_TIME, time)
        }
        db?.insert(ToDoDbNameClass.TABLE_NAME, null,values)
    }

    override fun deleteFromDb(id: String) {
        val selection= BaseColumns._ID + "=$id"
        db?.delete(ToDoDbNameClass.TABLE_NAME, selection,null)
    }

    override suspend fun update(title: String, desc: String, time: String, id: Int) {
        val selection= BaseColumns._ID + "=$id"
        val values = ContentValues().apply{
            put(ToDoDbNameClass.COLUMN_NAME_TITLE, title)
            put(ToDoDbNameClass.COLUMN_NAME_DESC, desc)
            put(ToDoDbNameClass.COLUMN_NAME_TIME, time)
        }
        db?.update(ToDoDbNameClass.TABLE_NAME, values,selection, null)
    }

    @SuppressLint("Range")
    override suspend fun read(searchText: String): ArrayList<TaskItem> {
        val dataList = ArrayList<TaskItem>()
        val selection="${ToDoDbNameClass.COLUMN_NAME_TITLE} like ?"
        val cursor = db?.query(ToDoDbNameClass.TABLE_NAME, null, selection, arrayOf("%$searchText%"), null,null,null)
        while(cursor?.moveToNext()!!){
            val dataTitle=cursor.getString(cursor.getColumnIndex(ToDoDbNameClass.COLUMN_NAME_TITLE))
            val dataId=cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val time=cursor.getString(cursor.getColumnIndex(ToDoDbNameClass.COLUMN_NAME_TIME))
            val desc=cursor.getString(cursor.getColumnIndex(ToDoDbNameClass.COLUMN_NAME_DESC))
            val item=TaskItem()
            item.title=dataTitle
            item.desc=desc
            item.id=dataId
            item.time=time
            dataList.add(item)
        }
        cursor.close()
        return dataList
    }
}