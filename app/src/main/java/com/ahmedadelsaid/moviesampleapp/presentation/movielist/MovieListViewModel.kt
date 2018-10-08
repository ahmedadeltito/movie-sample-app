package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import com.ahmedadelsaid.moviesampleapp.base.BaseViewModel
import com.ahmedadelsaid.moviesampleapp.domain.usecase.MovieUseCase
import javax.inject.Inject

class MovieListViewModel
@Inject
constructor(private val useCase: MovieUseCase)
    : BaseViewModel() {

    var moviesLiveData = useCase.movieLiveData()

    fun fetchMovies() = useCase.getMovies()

    fun refresh() = useCase.getMovies()

    override fun onCleared() {
        super.onCleared()
        useCase.clear()
    }

}