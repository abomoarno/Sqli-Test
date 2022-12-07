package com.afrimoov.sqlitest.repositories

import com.afrimoov.sqlitest.api.ApiDataSources
import com.afrimoov.sqlitest.api.ApiRequest
import com.afrimoov.sqlitest.api.ApiResponse
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(var api: ApiDataSources) : EmployeesRepository {

    override suspend fun getListEmployees(request: ApiRequest) : ApiResponse {
        return api.getListEmployees(request)
    }

}