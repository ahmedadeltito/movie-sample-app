package com.ahmedadelsaid.moviesampleapp.domain.responseresult

import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie

/**
 * Movies Response Result is like a bridge between handling the success, error and response from data layer to presentation layer.
 */

data class MoviesResponseResult(
        val networkState: NetworkState,
        val movies: List<Movie>? = null
)