package com.example.domain.repository

import com.example.appoint.data.models.Business
import com.example.data.models.Service
import org.litote.kmongo.coroutine.CoroutineDatabase

class ServiceRepository(
    db: CoroutineDatabase
) {
    val services = db.getCollection<Service>()

    suspend fun getServiceById(
        serviceId: String
    ): Service? {
        return services.findOneById(serviceId)
    }

    suspend fun addDummyBusiness(
        businessId: String
    ){
        services.insertOne(
            Service(
                serviceName = "Tuns barbati",
                businessId = businessId,
                servicePrice = 40.0F
            )
        )

        services.insertOne(
            Service(
                serviceName = "Tuns femei",
                businessId = businessId,
                servicePrice = 55.0F
            )
        )

        services.insertOne(
            Service(
                serviceName = "Tuns crocodili",
                businessId = businessId,
                servicePrice = 120.0F
            )
        )
    }
}