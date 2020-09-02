package com.michaeludjiawan.testproject.data.repository

import androidx.paging.PagingData
import com.michaeludjiawan.testproject.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<PagingData<User>>
}