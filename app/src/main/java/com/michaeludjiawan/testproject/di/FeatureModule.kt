package com.michaeludjiawan.testproject.di

import com.michaeludjiawan.testproject.data.repository.AccountRepository
import com.michaeludjiawan.testproject.data.repository.AccountRepositoryImpl
import com.michaeludjiawan.testproject.data.repository.UserRepository
import com.michaeludjiawan.testproject.data.repository.UserRepositoryImpl
import com.michaeludjiawan.testproject.ui.home.HomeViewModel
import com.michaeludjiawan.testproject.ui.profile.ProfileEditViewModel
import com.michaeludjiawan.testproject.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<AccountRepository> { AccountRepositoryImpl(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { ProfileEditViewModel(get()) }
}