package com.michaeludjiawan.testproject.di

import com.michaeludjiawan.testproject.data.repository.UserRepository
import com.michaeludjiawan.testproject.data.repository.UserRepositoryImpl
import com.michaeludjiawan.testproject.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }

    viewModel { HomeViewModel(get()) }
}