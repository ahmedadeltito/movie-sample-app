package com.ahmedadelsaid.moviesampleapp.data.repository.remote

import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import com.ahmedadelsaid.moviesampleapp.data.response.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API Endpoints
 */

interface MovieAPI {

    @GET("/3/movie/popular")
    fun getMovies(@Query("page") pageNumber: Int): Single<MovieResponse>

    @GET("/3/movie/{id}")
    fun getMovie(@Path("id") movieId: Int): Single<MovieEntity>

}