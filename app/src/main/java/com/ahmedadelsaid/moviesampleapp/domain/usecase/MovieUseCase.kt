package com.ahmedadelsaid.moviesampleapp.domain.usecase

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmedadelsaid.moviesampleapp.data.repository.MoviesRepo
import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.domain.responseresult.MovieResponseResult
import com.ahmedadelsaid.moviesampleapp.domain.responseresult.MoviesResponseResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * MovieUseCase is the user case of movie component that handle all stuff between repository and view model or presenter layer.
 */

class MovieUseCase
@Inject
constructor(private val moviesRepo: MoviesRepo) {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val moviesResponseResult: MutableLiveData<MoviesResponseResult> = MutableLiveData()
    private val movieResponseResult: MutableLiveData<MovieResponseResult> = MutableLiveData()
    private val filterResponseResult: MutableLiveData<MoviesResponseResult> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getMovies(pageNumber: Int) {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(moviesRepo.getMovies(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe
                {
                    moviesResponseResult.postValue(MoviesResponseResult(NetworkState.LOADING))
                }
                .subscribe(
                        { moviesList ->
                            moviesList?.let {
                                moviesResponseResult.postValue(MoviesResponseResult(NetworkState.LOADED, it))
                            } ?: run {
                                moviesResponseResult.postValue(MoviesResponseResult(NetworkState.error("data is finished!")))
                            }
                        },
                        { throwable ->
                            moviesResponseResult.postValue(MoviesResponseResult(NetworkState.error(
                                    throwable.message ?: "Something Went Wrong!")))
                        }
                )
        )
    }

    @SuppressLint("CheckResult")
    fun getMovie(movieId: Int) {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(moviesRepo.getMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe
                {
                    movieResponseResult.postValue(MovieResponseResult(NetworkState.LOADING))
                }
                .subscribe(
                        { movie ->
                            movie?.let {
                                movieResponseResult.postValue(MovieResponseResult(NetworkState.LOADED, it))
                            } ?: run {
                                movieResponseResult.postValue(MovieResponseResult(NetworkState.error("data is finished!")))
                            }
                        },
                        { throwable ->
                            movieResponseResult.postValue(MovieResponseResult(NetworkState.error(
                                    throwable.message ?: "Something Went Wrong!")))
                        }
                )
        )
    }

    @SuppressLint("CheckResult")
    fun filterMovies(pageNumber: Int, startDate: Date, endDate: Date) {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(moviesRepo.filterMovies(pageNumber, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe
                {
                    filterResponseResult.postValue(MoviesResponseResult(NetworkState.LOADING))
                }
                .subscribe(
                        { moviesList ->
                            moviesList?.let {
                                filterResponseResult.postValue(MoviesResponseResult(NetworkState.LOADED, it))
                            } ?: run {
                                filterResponseResult.postValue(MoviesResponseResult(NetworkState.error("data is finished!")))
                            }
                        },
                        { throwable ->
                            filterResponseResult.postValue(MoviesResponseResult(NetworkState.error(
                                    throwable.message ?: "Something Went Wrong!")))
                        }
                )
        )
    }

    fun moviesLiveData(): LiveData<MoviesResponseResult> = moviesResponseResult

    fun movieLiveData(): LiveData<MovieResponseResult> = movieResponseResult

    fun filterLiveData(): LiveData<MoviesResponseResult> = filterResponseResult

    fun clear() {
        compositeDisposable.clear()
    }
}