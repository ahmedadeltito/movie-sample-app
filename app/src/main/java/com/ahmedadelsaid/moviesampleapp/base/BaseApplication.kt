package com.ahmedadelsaid.moviesampleapp.base

import android.app.Application
import com.ahmedadelsaid.moviesampleapp.BuildConfig
import com.ahmedadelsaid.moviesampleapp.di.components.ApplicationComponent
import com.ahmedadelsaid.moviesampleapp.di.components.DaggerApplicationComponent
import com.ahmedadelsaid.moviesampleapp.di.modules.ApplicationModule
import com.ahmedadelsaid.moviesampleapp.di.modules.DataModule
import com.ahmedadelsaid.moviesampleapp.di.modules.NetworkModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * BaseApplication is the base application class of the app that we initialize inside it Timper, LeakCanary and Dagger Modules.
 */

class BaseApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Suppress("DEPRECATION")
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return
            }
            LeakCanary.install(this)
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(NetworkModule())
                .dataModule(DataModule(this))
                .applicationModule(ApplicationModule(this))
                .build()

    }

}