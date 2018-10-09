package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedadelsaid.moviesampleapp.R
import com.ahmedadelsaid.moviesampleapp.base.BaseApplication
import com.ahmedadelsaid.moviesampleapp.base.ViewModelFactory
import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.presentation.BaseActivity
import com.ahmedadelsaid.moviesampleapp.presentation.movielist.adapter.MovieAdapter
import com.ahmedadelsaid.moviesampleapp.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MovieListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MovieListViewModel
    private lateinit var adapter: MovieAdapter

    private var networkState = NetworkState.LOADED

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

    private fun fetchMovies(pageNumber: Int = 1) {
        viewModel.fetchMovies(pageNumber)
    }

    private fun observeMoviesList() {
        viewModel.moviesLiveData.observe(this, Observer { moviesLiveData ->
            networkState = moviesLiveData.networkState
            movies_swipe_refresh_layout.post {
                movies_swipe_refresh_layout.isRefreshing =
                        moviesLiveData.networkState.status == NetworkState.LOADING.status
            }
            when (moviesLiveData.networkState) {
                NetworkState.LOADED -> {
                    adapter.add(moviesLiveData.movies)
                }
                else -> {
                    networkState = NetworkState.error(networkState.message)
                    showEmptyList(networkState.message != null, networkState.message)
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = MovieAdapter(ArrayList())
        movies_list_view.adapter = adapter
        movies_list_view.addOnScrollListener(object : EndlessRecyclerViewScrollListener(movies_list_view.layoutManager as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                fetchMovies(page)
            }
        })
    }

    private fun initSwipeToRefresh() {
        movies_swipe_refresh_layout.setOnRefreshListener {
            adapter.clear()
            fetchMovies()
        }
    }

    private fun showEmptyList(show: Boolean, message: String?) {
        message?.let {
            toast(it)
            empty_list.text = it
        }
        if (show) {
            empty_list.visibility = View.VISIBLE
            movies_list_view.visibility = View.GONE
        } else {
            empty_list.visibility = View.GONE
            movies_list_view.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.cleanObservables()
    }
}
