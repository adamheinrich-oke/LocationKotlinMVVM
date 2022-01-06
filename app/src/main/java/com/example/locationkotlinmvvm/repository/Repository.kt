package com.example.locationkotlinmvvm.repository

import com.example.locationkotlinmvvm.api.RetrofitInstance
import com.example.locationkotlinmvvm.model.Coordinates
import com.example.locationkotlinmvvm.model.GetDistance
import retrofit2.Response

class Repository {

    suspend fun getDistance(): Response<GetDistance> = RetrofitInstance.api.get()
    suspend fun pushCoordinates(data: Coordinates): Response<Coordinates> =
        RetrofitInstance.api.push(data)

}