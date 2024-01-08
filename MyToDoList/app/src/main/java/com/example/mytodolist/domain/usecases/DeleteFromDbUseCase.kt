package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.repository.DbRepository

class DeleteFromDbUseCase(val repository: DbRepository) {
    fun execute(id: String){
        repository.deleteFromDb(id)
    }
}