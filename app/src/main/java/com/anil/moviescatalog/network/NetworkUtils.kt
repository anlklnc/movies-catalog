package com.anil.moviescatalog.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

object NetworkUtils {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return withContext(Dispatchers.IO) {
            try {
                Result.Success(apiCall.invoke())
            } catch (e: IOException) {
                when (e.message) {
                    "Unauthorized" -> Result.Unauthorized
                    "Forbidden" -> Result.Forbidden
                    "Not Found" -> Result.NotFound
                    "Internal Server Error" -> Result.InternalServerError
                    else -> Result.Error(e)
                }
            }
        }
    }
}