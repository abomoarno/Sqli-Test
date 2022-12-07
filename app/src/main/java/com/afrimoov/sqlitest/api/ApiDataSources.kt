package com.afrimoov.sqlitest.api

interface ApiDataSources {

    suspend fun getListEmployees(request: ApiRequest): ApiResponse

}