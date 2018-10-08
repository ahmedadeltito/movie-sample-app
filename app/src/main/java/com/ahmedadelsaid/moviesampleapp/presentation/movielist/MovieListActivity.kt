package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ahmedadelsaid.moviesampleapp.R
import com.ahmedadelsaid.moviesampleapp.base.BaseApplication
import com.ahmedadelsaid.moviesampleapp.base.ViewModelFactory
import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.presentation.BaseActivity
import com.ahmedadelsaid.moviesampleapp.presentation.movielist.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.jetbrains.anko.toast
import timber.log.Timber
import javax.inject.Inject

class MovieListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MovieListViewModel
    private lateinit var adapter: MovieAdapter

    override fun injectActivity() {
        (application as? BaseApplication)?.applicationComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)

        observeMoviesList()
        initAdapter()
        initSwipeToRefresh()
        fetchMovies()

    }

    private fun fetchMovies() {
        viewModel.fetchMovies()
    }

    private fun observeMoviesList() {
        viewModel.moviesLiveData.observe(this, Observer { moviesLiveData ->
            adapter.setNetworkState(moviesLiveData.networkState)
            movies_swipe_refresh_layout.post {
                movies_swipe_refresh_layout.isRefreshing =
                        moviesLiveData.networkState.status == NetworkState.LOADING.status
            }
            when (moviesLiveData.networkState) {
                NetworkState.LOADED -> {
                    Timber.d("movies list: ${moviesLiveData?.movies?.size}")
                    showEmptyList(moviesLiveData?.movies?.size == 0)
                    adapter.submitList(moviesLiveData?.movies)
                }
                else -> {
                    moviesLiveData.networkState.message?.let { toast(it) }
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = MovieAdapter { viewModel.refresh() }
        movies_list_view.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        movies_swipe_refresh_layout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            empty_list.visibility = View.VISIBLE
            movies_list_view.visibility = View.GONE
        } else {
            empty_list.visibility = View.GONE
            movies_list_view.visibility = View.VISIBLE
        }
    }

}
