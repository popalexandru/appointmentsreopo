package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class TestDto(
    @BsonId
    val id: String = ObjectId().toString(),
    val text: String
)
