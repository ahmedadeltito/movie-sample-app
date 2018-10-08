package com.ahmedadelsaid.moviesampleapp.data.mapper

import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import javax.inject.Inject

class MovieMapper constructor() {
    fun fromDb(from: MovieEntity) = Movie(
            from.id,
            from.overview,
            from.originalLanguage,
            from.originalTitle,
            from.title,
            from.releaseDate,
            from.voteAverage,
            from.popularity,
            from.voteCount
    )
}