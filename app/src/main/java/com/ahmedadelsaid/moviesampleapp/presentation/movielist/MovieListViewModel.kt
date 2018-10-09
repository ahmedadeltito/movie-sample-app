package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import androidx.lifecycle.ViewModel
import com.ahmedadelsaid.moviesampleapp.domain.usecase.MovieUseCase
import java.util.*
import javax.inject.Inject

/**
 * MovieListViewModel is the view model layer of movie list between use case and presentation layer.
 */

class MovieListViewModel
@Inject
constructor(private val useCase: MovieUseCase)
    : ViewModel() {

    var moviesLiveData = useCase.moviesLiveData()

    var filterLiveData = useCase.filterLiveData()

    fun fetchMovies(pageNumber: Int) = useCase.getMovies(pageNumber)

    fun filterMovies(pageNumber: Int, startDate: Date, endDate: Date) = useCase.filterMovies(pageNumber, startDate, endDate)

    fun cleanObservables() = useCase.clear()

    override fun onCleared() {
        super.onCleared()
        cleanObservables()
    }

}