package com.ahmedadelsaid.moviesampleapp.domain.model

/**
 * Movie Response that will be appears in the presentation layer.
 */

data class Movie(

        val id: Int? = null,
        val title: String? = null,
        val overview: String? = null,
        val releaseDate: String? = null,
        val voteAverage: Double? = null,
        val voteCount: Int? = null,
        val backdropPath: String? = null,
        val popularity: Double? = null

)