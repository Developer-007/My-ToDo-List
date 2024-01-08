package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.repository.DbRepository

class CloseDbUseCase(val repository: DbRepository) {
    fun execute(){
        repository.closeDb()
    }
}