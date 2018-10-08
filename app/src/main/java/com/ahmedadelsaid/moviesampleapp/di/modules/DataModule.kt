package com.ahmedadelsaid.moviesampleapp.di.modules

import android.content.Context
import androidx.room.Room
import com.ahmedadelsaid.moviesampleapp.data.mapper.MovieMapper
import com.ahmedadelsaid.moviesampleapp.data.repository.MoviesRepo
import com.ahmedadelsaid.moviesampleapp.data.repository.local.DatabaseManager
import com.ahmedadelsaid.moviesampleapp.data.repository.local.MovieDao
import com.ahmedadelsaid.moviesampleapp.data.repository.pagelistboundaries.MovieListBoundaryCallback
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI
import com.ahmedadelsaid.moviesampleapp.di.scopes.MovieApplicationScope
import com.ahmedadelsaid.moviesampleapp.domain.usecase.MovieUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DataModule
@Inject
constructor(context: Context) {

    private val databaseManager: DatabaseManager
            = DatabaseManager.getInstance(context)

    @Provides
    internal fun provideMovieDao()
            = databaseManager.movieDao()

    @Provides
    internal fun provideMovieListBoundaryCallback(local: MovieDao, remote: MovieAPI)
            = MovieListBoundaryCallback(local, remote)

    @Provides
    internal fun provideRepository(local: MovieDao, remote: MovieAPI, listCallback: MovieListBoundaryCallback)
            = MoviesRepo(local, remote, listCallback)

    @Provides
    internal fun providesMovieUseCase(moviesRepo: MoviesRepo)
            = MovieUseCase(moviesRepo)

}