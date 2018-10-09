package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import com.ahmedadelsaid.moviesampleapp.base.BaseViewModel
import com.ahmedadelsaid.moviesampleapp.domain.usecase.MovieUseCase
import javax.inject.Inject

class MovieListViewModel
@Inject
constructor(private val useCase: MovieUseCase)
    : BaseViewModel() {

    var moviesLiveData = useCase.movieLiveData()

    fun fetchMovies(pageNumber: Int) = useCase.getMovies(pageNumber)
    fun cleanObservables() = useCase.clear()

    override fun onCleared() {
        super.onCleared()
        cleanObservables()
    }

}