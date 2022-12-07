package com.afrimoov.sqlitest.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    @GET("{users}")
    suspend fun getListEmployees(@Path(value = "users") endPoint:String = "users", @QueryMap requestMap: Map<String, String>): ApiResponse


}