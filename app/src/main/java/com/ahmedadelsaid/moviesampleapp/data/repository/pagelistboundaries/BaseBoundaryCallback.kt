package com.ahmedadelsaid.moviesampleapp.data.repository.pagelistboundaries

import androidx.paging.PagedList
import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import com.ahmedadelsaid.moviesampleapp.data.repository.local.MovieDao
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI

open class BaseBoundaryCallback(var local: MovieDao, var remote: MovieAPI)
    : PagedList.BoundaryCallback<MovieEntity>() {

    var page: Int = 0

    init {
        page = 0
    }

    fun nextPage() {
        page++
    }
}