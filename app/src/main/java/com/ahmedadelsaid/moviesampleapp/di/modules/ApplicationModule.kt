package com.ahmedadelsaid.moviesampleapp.di.modules

import android.content.Context
import com.ahmedadelsaid.moviesampleapp.di.scopes.MovieApplicationScope
import dagger.Module
import dagger.Provides

/**
 * Application Module which provides app context.
 */

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @MovieApplicationScope
    internal fun provideContext()
            = context
}