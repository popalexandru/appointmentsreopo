package com.example.domain.service

import com.example.data.models.Example
import com.example.domain.repository.ExamplesRepository

class ExampleService(
    private val repository: ExamplesRepository
) {

    suspend fun getExById(exerciceId: String) : Example?{
        return repository.getExampleById(exerciceId)
    }
}