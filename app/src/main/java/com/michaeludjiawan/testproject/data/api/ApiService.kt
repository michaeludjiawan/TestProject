package com.michaeludjiawan.testproject.data.api

import com.michaeludjiawan.testproject.data.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int
    ): ListResponse<User>
}