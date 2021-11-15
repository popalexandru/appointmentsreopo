package com.example.domain.repository

import com.example.data.models.Example
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ExamplesRepository(
    db: CoroutineDatabase
) {
    val examples = db.getCollection<Example>()

    suspend fun getExampleById(exerciceId: String): Example?{
        return examples.findOne(Example::exId eq exerciceId)
    }
}