package com.ahmedadelsaid.moviesampleapp.di.components

import com.ahmedadelsaid.moviesampleapp.di.modules.ApplicationModule
import com.ahmedadelsaid.moviesampleapp.di.modules.DataModule
import com.ahmedadelsaid.moviesampleapp.di.modules.NetworkModule
import com.ahmedadelsaid.moviesampleapp.di.modules.ViewModelModule
import com.ahmedadelsaid.moviesampleapp.di.scopes.MovieApplicationScope
import com.ahmedadelsaid.moviesampleapp.presentation.movielist.MovieListActivity
import com.ahmedadelsaid.moviesampleapp.presentation.movielist.MovieListViewModel
import dagger.Component

@MovieApplicationScope
@Component(modules = [
    NetworkModule::class,
    DataModule::class,
    ApplicationModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    fun inject(movieListActivity: MovieListActivity)

}