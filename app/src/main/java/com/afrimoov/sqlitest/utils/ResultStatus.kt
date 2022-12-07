package com.afrimoov.sqlitest.utils

sealed interface ResultStatus<out T>{
    data class Success<T>(val data:T) : ResultStatus<T>
    data class Error(val message: String? = null) : ResultStatus<Nothing>
    object Loading : ResultStatus<Nothing>
}