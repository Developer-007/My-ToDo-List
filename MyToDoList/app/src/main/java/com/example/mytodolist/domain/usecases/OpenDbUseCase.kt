package com.example.mytodolist.domain.usecases

import com.example.mytodolist.domain.repository.DbRepository

class OpenDbUseCase(val repository: DbRepository) {
    fun execute(){
        repository.openDb()
    }
}