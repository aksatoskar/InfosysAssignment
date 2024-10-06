package com.example.infosysassignment

import android.app.Application
import com.example.infosysassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RentalApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RentalApplication)
            modules(
                listOf(
                    appModule
                )
            )
        }
    }
}