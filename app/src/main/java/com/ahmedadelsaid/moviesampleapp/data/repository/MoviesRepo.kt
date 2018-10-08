package com.ahmedadelsaid.moviesampleapp.data.repository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.ahmedadelsaid.moviesampleapp.data.mapper.MovieMapper
import com.ahmedadelsaid.moviesampleapp.data.repository.local.MovieDao
import com.ahmedadelsaid.moviesampleapp.data.repository.pagelistboundaries.MovieListBoundaryCallback
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class MoviesRepo
@Inject
constructor(private val local: MovieDao,
            private val remote: MovieAPI,
            private val listCallback: MovieListBoundaryCallback) {

    private val mConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(5)
            .setPageSize(20)
            .build()

    private val mapper = MovieMapper()

    fun getMovies(): Flowable<PagedList<Movie>> {
        return RxPagedListBuilder(local.getMovies.map { mapper.fromDb(it) }, mConfig)
                .setBoundaryCallback(listCallback)
                .buildFlowable(BackpressureStrategy.LATEST)
    }

//    fun getMovie(id: Int): Flowable<Movie> {
//        Single.concat(
//                local.getMovie(id),
//                remote.getMovie(id).doOnSuccess { movie ->
//                    local.insertMovie(movie)
//                })
//        return Single.concat(local.getMovie(id),
//                remote.getMovie(id).doOnSuccess(
//                        {
//                            data -> local.insertMovie(data)
//                        }
//                ))
//                .onErrorResumeNext(Function<Throwable, Publisher<out Movie>>
//                {
//                    Flowable.error(it)
//                } as io.reactivex.functions.Function<Throwable, Publisher<out Movie>>)
//    }

    fun clearDatabase(): Single<Int> {
        return Observable.fromCallable { local.deleteAll() }.firstOrError()
    }
}