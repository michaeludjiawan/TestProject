package com.michaeludjiawan.testproject.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeludjiawan.testproject.data.model.Account
import com.michaeludjiawan.testproject.data.repository.AccountRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val mutableAccount = MutableLiveData<Account>()
    val account: LiveData<Account> = mutableAccount

    fun getAccount() = viewModelScope.launch {
        mutableAccount.value = accountRepository.getAccount()
    }
}