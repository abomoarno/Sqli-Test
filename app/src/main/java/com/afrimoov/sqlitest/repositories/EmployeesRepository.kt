package com.afrimoov.sqlitest.repositories

import com.afrimoov.sqlitest.api.ApiRequest
import com.afrimoov.sqlitest.api.ApiResponse

interface EmployeesRepository {

    suspend fun getListEmployees(request: ApiRequest) : ApiResponse

}