package com.example.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    val name: String,
    val surname: String,
    val email: String,
    val profileImageUrl : String,
    val password : String,
    @BsonId
    val id : String = ObjectId().toString()
)
