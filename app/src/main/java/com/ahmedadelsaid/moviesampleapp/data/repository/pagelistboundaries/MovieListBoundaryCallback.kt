package com.ahmedadelsaid.moviesampleapp.data.repository.pagelistboundaries

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.NonNull
import androidx.paging.PagedList
import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import com.ahmedadelsaid.moviesampleapp.data.repository.local.MovieDao
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import com.ahmedadelsaid.moviesampleapp.utils.safeLet
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListBoundaryCallback
@Inject
constructor(private val local: MovieDao, private val remote: MovieAPI)
    : PagedList.BoundaryCallback<Movie>() {

    private var page = 0

    /**
     * Called when zero items are returned from an initial load of the PagedList's data source.
     */
    override fun onZeroItemsLoaded() {
        page = 1
        loadAndSave()
    }

    /**
     * Called when the item at the end of the PagedList has been loaded, and access has
     * occurred within Config.prefetchDistance of it.
     * No more data will be appended to the PagedList after this item.
     *
     * @param itemAtEnd The first item of PagedList
     */
    override fun onItemAtEndLoaded(@NonNull itemAtEnd: Movie) {
        super.onItemAtEndLoaded(itemAtEnd)
        page++
        loadAndSave()
    }

    @SuppressLint("CheckResult")
    private fun loadAndSave() {
        remote.getMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { movieResponse ->
                            safeLet(movieResponse.page, movieResponse.results) { _, moviesList ->
                                moviesList.forEach { movie ->
                                    movie?.let {
                                        local.insertMovie(movie)
                                    }
                                }
                            }
                        },
                        { throwable ->
                            Log.e("ZERO_ITEM_POPULAR_ERROR", throwable.message)
                        }
                )
    }
}