package com.ahmedadelsaid.moviesampleapp.domain.usecase

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmedadelsaid.moviesampleapp.data.repository.MoviesRepo
import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.domain.responseresult.MovieResponseResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieUseCase
@Inject
constructor(private val moviesRepo: MoviesRepo) {

    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private val movieResponseResult: MutableLiveData<MovieResponseResult> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getMovies() {
        clear()
        compositeDisposable?.add(moviesRepo.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe
                { subscription ->
                    subscription.request(java.lang.Long.MAX_VALUE)
                    movieResponseResult.postValue(MovieResponseResult(NetworkState.LOADING))
                }
                .subscribe(
                        { pagedListMovieEntities ->
                            movieResponseResult.postValue(MovieResponseResult(NetworkState.LOADED, pagedListMovieEntities))
                        },
                        { throwable ->
                            movieResponseResult.postValue(MovieResponseResult(NetworkState.error(throwable.message
                                    ?: "Something Went Wrong!")))
                        }
                )
        )
    }

    fun movieLiveData(): LiveData<MovieResponseResult> = movieResponseResult

    fun clear() {
        compositeDisposable?.clear()
    }
}