package com.ahmedadelsaid.moviesampleapp.data.repository.remote

import com.ahmedadelsaid.moviesampleapp.data.response.MovieResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("/3/movie/top_rated")
    abstract fun getMovies(@Query("page") pageNumber: Int): Single<MovieResponse>

    @GET("/3/movie/top_rated")
    abstract fun getMovie(@Query("id") movieId: Int): Single<MovieResponse>

}