package com.example.domain.repository

import com.example.appoint.data.models.Business
import org.litote.kmongo.coroutine.CoroutineDatabase

class BusinessRepository(
    db: CoroutineDatabase
) {
    val business = db.getCollection<Business>()

    suspend fun getBusinessById(
        businessId: String
    ): Business? {
        //addDummyBusiness()
        return business.findOneById(businessId)
    }

    private suspend fun addDummyBusiness(){
        business.insertOne(
            Business(
                businessName = "Pop Alexandru Vasile PFA",
                businessImgUrl = "https://www.replicahd.ro/wp-content/uploads/2020/05/salon-frizerie-coafura-infrumusetare.jpg",
                rating = 5.0F,
                categoriesList = emptyList(),
                userReservation = null
            )
        )
    }
}