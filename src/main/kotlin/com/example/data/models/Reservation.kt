package com.example.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Reservation(
    @BsonId
    val reservationId: String = ObjectId().toString(),
    val userId: String,
    val businessId: String,
    val businessName: String,
    val timestampDone: Long,
    val timestampDue: Long
)
