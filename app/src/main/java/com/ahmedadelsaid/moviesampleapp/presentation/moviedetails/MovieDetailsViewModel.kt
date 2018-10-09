package com.ahmedadelsaid.moviesampleapp.presentation.moviedetails

import androidx.lifecycle.ViewModel
import com.ahmedadelsaid.moviesampleapp.domain.usecase.MovieUseCase
import javax.inject.Inject

/**
 * MovieDetailsViewModel is the view model layer of movie details between use case and presentation layer.
 */

class MovieDetailsViewModel
@Inject
constructor(private val useCase: MovieUseCase)
    : ViewModel() {

    var movieLiveData = useCase.movieLiveData()

    fun fetchMovie(movieId: Int) = useCase.getMovie(movieId)

    fun cleanObservables() = useCase.clear()

    override fun onCleared() {
        super.onCleared()
        cleanObservables()
    }

}