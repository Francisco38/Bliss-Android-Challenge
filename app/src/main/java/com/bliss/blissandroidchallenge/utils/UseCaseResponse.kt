package com.bliss.blissandroidchallenge.utils

sealed class UseCaseResponse<out T>{
    data class Success<T>(val data: T) : UseCaseResponse<T>()
    data class Error(val error: String) : UseCaseResponse<Nothing>()
}