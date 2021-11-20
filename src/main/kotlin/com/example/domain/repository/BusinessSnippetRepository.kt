package com.example.domain.repository

import com.example.appoint.data.models.BusinessSnippet
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.regex

class BusinessSnippetRepository(
    val db: CoroutineDatabase
) {
    val businessSnippets = db.getCollection<BusinessSnippet>()

    suspend fun getBusinessSnippets(
        query: String,
        page: Int,
        pageSize: Int,
    ): List<BusinessSnippet>{
        addDummyBusinessSnippet()

        return businessSnippets
            .find(BusinessSnippet::businessName regex Regex("(?i).*$query.*"))
            .skip(page * pageSize)
            .limit(pageSize)
            .toList()
    }

    suspend fun addDummyBusinessSnippet(){
        businessSnippets.insertOne(
            BusinessSnippet(
                businessName = "Pop Alexandru Vasile PFA",
                businessImgUrl = "",
                rating = 5.0F,
                categoriesList = emptyList()
            )
        )
    }
}