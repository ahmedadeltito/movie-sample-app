package com.ahmedadelsaid.moviesampleapp.di.modules

import android.content.Context
import com.ahmedadelsaid.moviesampleapp.di.scopes.MovieApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @MovieApplicationScope
    internal fun provideContext()
            = context
}