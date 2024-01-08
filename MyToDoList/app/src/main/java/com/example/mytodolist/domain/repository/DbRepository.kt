package com.example.mytodolist.domain.repository

import com.example.mytodolist.data.model.TaskItem

interface DbRepository {
    fun openDb()
    fun closeDb()
    suspend fun insertToDb(title: String, desc: String, time: String)
    fun deleteFromDb(id: String)
    suspend fun update(title: String, desc: String, time: String, id: Int)
    suspend fun read(searchText:String): ArrayList<TaskItem>
}