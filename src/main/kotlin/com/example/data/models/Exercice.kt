package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Exercice(
    val exerciceId: String,
    val workoutId: String
)
