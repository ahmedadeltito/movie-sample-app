package com.ahmedadelsaid.moviesampleapp.data.mapper

import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import javax.inject.Inject

/**
 * MovieMapper is the mapper class that we will use in our presentation layer, since it's blueprint class is MovieEntity that
 * our room makes its transactions on it.
 */

class MovieMapper {
    fun fromDb(from: MovieEntity) = Movie(
            from.id,
            from.title,
            from.overview,
            from.releaseDate,
            from.voteAverage,
            from.voteCount,
            from.backdropPath,
            from.popularity
    )
}