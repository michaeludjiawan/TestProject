package com.michaeludjiawan.testproject.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.michaeludjiawan.testproject.data.api.ApiService
import com.michaeludjiawan.testproject.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override fun getUsers(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { UserPagingSource(apiService) }
        ).flow
    }
}