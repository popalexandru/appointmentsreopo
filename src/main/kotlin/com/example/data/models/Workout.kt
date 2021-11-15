package com.example.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Workout(
    @BsonId
    val workoutId: String = ObjectId().toString(),
    val userId: String,
    val date: String,
    val isDone: Boolean
)
