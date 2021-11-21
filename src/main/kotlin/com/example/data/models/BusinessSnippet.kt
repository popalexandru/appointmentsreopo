package com.example.appoint.data.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class BusinessSnippet(
    @BsonId
    val businessSnippetId: String = ObjectId().toString(),
    val businessId: String,
    val businessName: String,
    val businessImgUrl: String,
    val rating: Float,
    val categoriesList: List<String>
)
