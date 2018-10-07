package com.ahmedadelsaid.moviesampleapp.data.repository.pagelistboundaries

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.NonNull
import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import com.ahmedadelsaid.moviesampleapp.data.repository.local.MovieDao
import com.ahmedadelsaid.moviesampleapp.data.repository.remote.MovieAPI
import com.ahmedadelsaid.moviesampleapp.utils.safeLet
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListBoundaryCallback
@Inject
constructor(local: MovieDao, remote: MovieAPI)
    : BaseBoundaryCallback(local, remote) {

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
    override fun onItemAtEndLoaded(@NonNull itemAtEnd: MovieEntity) {
        super.onItemAtEndLoaded(itemAtEnd)

        nextPage()
        loadAndSave()
    }

    @SuppressLint("CheckResult")
    private fun loadAndSave() {
        remote.getMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { movieResponse ->
                            safeLet(movieResponse.page, movieResponse.results) { page, moviesList ->
                                this.page = page
                                if (page == 1) {
                                    moviesList.forEach { movie ->
                                        movie?.let {
                                            local.insertMovie(movie)
                                        }
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