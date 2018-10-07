package com.ahmedadelsaid.moviesampleapp.base

import android.app.Application
import com.ahmedadelsaid.moviesampleapp.di.components.ApplicationComponent
import com.ahmedadelsaid.moviesampleapp.di.components.DaggerApplicationComponent
import com.ahmedadelsaid.moviesampleapp.di.modules.ApplicationModule
import com.ahmedadelsaid.moviesampleapp.di.modules.DataModule
import com.ahmedadelsaid.moviesampleapp.di.modules.NetworkModule

class BaseApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(NetworkModule())
                .dataModule(DataModule(this))
                .applicationModule(ApplicationModule(this))
                .build()

    }

}