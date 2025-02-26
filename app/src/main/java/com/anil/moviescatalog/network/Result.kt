package com.anil.moviescatalog.network

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object NetworkError : Result<Nothing>()
    object Unauthorized : Result<Nothing>()
    object Forbidden : Result<Nothing>()
    object NotFound : Result<Nothing>()
    object InternalServerError : Result<Nothing>()
}