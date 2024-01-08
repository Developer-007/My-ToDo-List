package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.repository.DbRepository

class InsertToDbUseCase(val repository: DbRepository) {
    suspend fun execute(title: String, desc: String, time: String){
        repository.insertToDb(title, desc, time)
    }
}