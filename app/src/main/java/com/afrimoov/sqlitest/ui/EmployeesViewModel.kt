package com.afrimoov.sqlitest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afrimoov.sqlitest.api.ApiRequest
import com.afrimoov.sqlitest.api.ApiResponse
import com.afrimoov.sqlitest.models.ListItem
import com.afrimoov.sqlitest.repositories.EmployeesRepository
import com.afrimoov.sqlitest.utils.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val repository: EmployeesRepository
) : ViewModel(){

    private val _employeesList : MutableLiveData<ResultStatus<ApiResponse>> = MutableLiveData()
    val employeesList : LiveData<ResultStatus<ApiResponse>> = _employeesList

    var firstLoad = true

    val mItems : MutableList<ListItem.Employee> = mutableListOf()

    fun getListEmployees(request: ApiRequest, showLoading : Boolean = true){
        if (showLoading)
            _employeesList.postValue(ResultStatus.Loading)
        viewModelScope.launch(IO) {
            val result = try {
                ResultStatus.Success(repository.getListEmployees(request))
            }catch (exception : Exception){
                ResultStatus.Error()
            }
            _employeesList.postValue(result)
        }
    }
}