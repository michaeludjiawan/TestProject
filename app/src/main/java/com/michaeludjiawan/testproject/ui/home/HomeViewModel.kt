package com.michaeludjiawan.testproject.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.michaeludjiawan.testproject.data.model.User
import com.michaeludjiawan.testproject.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun getUsers(): Flow<PagingData<User>> {
        return userRepository.getUsers().cachedIn(viewModelScope)
    }
}