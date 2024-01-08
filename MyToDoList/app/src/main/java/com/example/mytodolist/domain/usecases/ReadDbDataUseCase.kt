package com.example.mytodolist.domain.usecases

import com.example.mytodolist.data.model.TaskItem
import com.example.mytodolist.domain.repository.DbRepository

class ReadDbDataUseCase(val repository: DbRepository) {
    suspend fun execute(searchText:String): ArrayList<TaskItem>{
        return repository.read(searchText)
    }
}