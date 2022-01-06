package com.example.locationkotlinmvvm.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.locationkotlinmvvm.model.Coordinates
import com.example.locationkotlinmvvm.model.GetDistance

interface SimpleApi {

    @GET("adamapi")
    suspend fun get(): Response<GetDistance>

    @POST("adamapi")
    suspend fun push(
        @Body post: Coordinates
    ): Response<Coordinates>

}