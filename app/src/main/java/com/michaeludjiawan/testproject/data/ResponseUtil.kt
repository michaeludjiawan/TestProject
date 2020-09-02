package com.michaeludjiawan.testproject.data

import retrofit2.Response

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call.invoke()
        Result.create(response)
    } catch (throwable: Throwable) {
        Result.Error(throwable)
    }
}