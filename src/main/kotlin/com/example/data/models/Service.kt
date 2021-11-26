package com.example.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Service(
    @BsonId
    val serviceId: String = ObjectId().toString(),
    val serviceName: String,
    val businessId: String,
    val servicePrice: Float
)
