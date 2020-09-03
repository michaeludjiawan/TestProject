package com.michaeludjiawan.testproject.data.repository

import com.michaeludjiawan.testproject.data.local.AccountDao
import com.michaeludjiawan.testproject.data.model.Account

class AccountRepositoryImpl(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun getAccount(): Account? {
        return accountDao.getAccount()
    }

    override suspend fun update(account: Account) {
        return accountDao.insert(account)
    }
}