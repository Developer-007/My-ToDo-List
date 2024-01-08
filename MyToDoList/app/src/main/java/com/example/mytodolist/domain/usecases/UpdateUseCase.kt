package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.repository.DbRepository

class UpdateUseCase(val repository: DbRepository) {
    suspend fun execute(title: String, desc: String, time: String, id: Int){
        repository.update(title, desc, time,  id)
    }
}