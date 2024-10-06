package com.example.infosysassignment.di

import com.example.infosysassignment.feature.authentication.data.api.AuthApi
import com.example.infosysassignment.feature.authentication.data.api.AuthService
import com.example.infosysassignment.feature.authentication.data.repository.AuthRepositoryImpl
import com.example.infosysassignment.feature.authentication.domain.repository.AuthRepository
import com.example.infosysassignment.feature.rental.data.api.RentalApi
import com.example.infosysassignment.feature.rental.data.api.RentalService
import com.example.infosysassignment.feature.rental.data.api.SpeedApi
import com.example.infosysassignment.feature.rental.data.api.SpeedService
import com.example.infosysassignment.feature.rental.data.repository.RentalNetworkRepositoryImpl
import com.example.infosysassignment.feature.rental.domain.repository.RentalRepository
import com.example.infosysassignment.view.viewmodel.RentalViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("AppScope")) {
        provideAppCoroutineScope()
    }
    single <AuthApi> { AuthService() }

    single<AuthRepository> {
        AuthRepositoryImpl(
            get()
        )
    }

    single <RentalApi> { RentalService() }

    single<RentalRepository> {
        RentalNetworkRepositoryImpl(
            get()
        )
    }

    single <SpeedApi> { SpeedService() }

    viewModel { RentalViewModel(get(), get()) }
}

private fun provideAppCoroutineScope(): CoroutineScope =
    CoroutineScope(SupervisorJob() + Dispatchers.Default)
