package com.michaeludjiawan.testproject.data.repository

import com.michaeludjiawan.testproject.data.model.Account

interface AccountRepository {
    suspend fun getAccount(): Account?
    suspend fun update(account: Account)
}