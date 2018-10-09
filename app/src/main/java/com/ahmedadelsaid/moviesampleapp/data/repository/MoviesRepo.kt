package com.ahmedadelsaid.moviesampleapp.data.repository

import com.ahmedadelsaid.moviesampleapp.data.mapper.MovieMapper
import com.ahmedadelsaid.moviesampleapp.data.repository.local.MovieDao
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import com.ahmedadelsaid.moviesampleapp.utils.CommonUtils
import com.ahmedadelsaid.moviesampleapp.utils.date.toDate
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * MoviesRepo is the repository data layer for movie module.
 */

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

    fun filterMovies(pageNumber: Int, startDate: Date, endDate: Date): Flowable<List<Movie>?> {

        val remoteMovie = remote.getMovies(pageNumber).map { movieResponse ->
            movieResponse.results?.asSequence()?.map { movieEntity ->
                mapper.fromDb(movieEntity)
            }?.filter { movie ->
                movie.releaseDate?.let { movieDate ->
                    movieDate.toDate(CommonUtils.DATE_FORMATE) in startDate..endDate
                } ?: run {
                    false
                }
            }?.toList()
        }

        return remoteMovie.toFlowable()
    }

    @Suppress("unused")
    fun clearDatabase(): Single<Int> {
        return Observable.fromCallable { local.deleteAll() }.firstOrError()
    }
}