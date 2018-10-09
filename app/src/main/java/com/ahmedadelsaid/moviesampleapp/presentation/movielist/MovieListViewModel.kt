package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import androidx.lifecycle.ViewModel
import com.ahmedadelsaid.moviesampleapp.domain.usecase.MovieUseCase
import java.util.*
import javax.inject.Inject

/**
 * MovieListViewModel is the view model layer between use case and presentation layer.
 */

class MovieListViewModel
@Inject
constructor(private val useCase: MovieUseCase)
    : ViewModel() {

    var moviesLiveData = useCase.movieLiveData()

    var filterLiveData = useCase.filterLiveData()

    fun fetchMovies(pageNumber: Int) = useCase.getMovies(pageNumber)

    fun filterMovies(pageNumber: Int, startDate: Date, endDate: Date) = useCase.filterMovies(pageNumber, startDate, endDate)

    fun cleanObservables() = useCase.clear()

    override fun onCleared() {
        super.onCleared()
        cleanObservables()

        val startDate = Calendar.getInstance()
        startDate.add(Calendar.YEAR, -20)
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.YEAR, -10)
    }

}