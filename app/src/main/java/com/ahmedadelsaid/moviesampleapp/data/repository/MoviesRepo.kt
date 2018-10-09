package com.ahmedadelsaid.moviesampleapp.data.repository

import android.util.Log
import com.ahmedadelsaid.moviesampleapp.data.mapper.MovieMapper
import com.ahmedadelsaid.moviesampleapp.data.repository.local.MovieDao
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepo
@Inject
constructor(private val local: MovieDao,
            private val remote: MovieAPI) {

    private val mapper = MovieMapper()

    fun getMovies(pageNumber: Int): Flowable<List<Movie>?> {

        val localMovie =
                local.getMovies.map { movieEntityList ->
                    movieEntityList.map { movieEntity ->
                        mapper.fromDb(movieEntity)
                    }
                }

        val remoteMovie = remote.getMovies(pageNumber).map { movieResponse ->
            movieResponse.results?.map { movieEntity ->
                if (pageNumber == 1)
                    local.insertMovie(movieEntity)
                mapper.fromDb(movieEntity)
            }
        }

        if (pageNumber == 1)
            return Single.concat<List<Movie>>(localMovie, remoteMovie)
        return remoteMovie.toFlowable()
    }

    fun clearDatabase(): Single<Int> {
        return Observable.fromCallable { local.deleteAll() }.firstOrError()
    }
}