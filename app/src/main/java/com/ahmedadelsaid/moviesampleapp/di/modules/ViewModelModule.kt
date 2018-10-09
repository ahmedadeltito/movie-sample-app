package com.ahmedadelsaid.moviesampleapp.di.modules

import androidx.lifecycle.ViewModel
import com.ahmedadelsaid.moviesampleapp.di.scopes.MovieApplicationScope
import com.ahmedadelsaid.moviesampleapp.di.scopes.ViewModelKey
import com.ahmedadelsaid.moviesampleapp.presentation.movielist.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * ViewModel Module that provides Movie List ViewModel.
 */

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindMovieListViewModel(viewModel: MovieListViewModel): ViewModel

}