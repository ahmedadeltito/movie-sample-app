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

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val movieResponseResult: MutableLiveData<MovieResponseResult> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getMovies(pageNumber: Int) {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(moviesRepo.getMovies(pageNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe
                {
                    movieResponseResult.postValue(MovieResponseResult(NetworkState.LOADING))
                }
                .subscribe(
                        { moviesList ->
                            moviesList?.let {
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

    fun movieLiveData(): LiveData<MovieResponseResult> = movieResponseResult

    fun clear() {
        compositeDisposable.clear()
    }
}