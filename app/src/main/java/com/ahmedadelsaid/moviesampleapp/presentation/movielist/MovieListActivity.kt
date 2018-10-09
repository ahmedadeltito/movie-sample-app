package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmedadelsaid.moviesampleapp.R
import com.ahmedadelsaid.moviesampleapp.base.BaseApplication
import com.ahmedadelsaid.moviesampleapp.base.ViewModelFactory
import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.presentation.BaseActivity
import com.ahmedadelsaid.moviesampleapp.presentation.moviedetails.MovieDetailsActivity
import com.ahmedadelsaid.moviesampleapp.presentation.movielist.adapter.MovieAdapter
import com.ahmedadelsaid.moviesampleapp.utils.CommonUtils
import com.ahmedadelsaid.moviesampleapp.utils.EndlessRecyclerViewScrollListener
import com.ahmedadelsaid.moviesampleapp.utils.date.Dates
import com.ahmedadelsaid.moviesampleapp.utils.safeLet
import com.borax12.materialdaterangepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_movie_list.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

/**
 * The main screen that is the entry point mostly of all the logic we have in this application.
 */

class MovieListActivity : BaseActivity(),
        DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MovieListViewModel
    private lateinit var adapter: MovieAdapter

    private var isFilter = false

    private var endDate: Date? = null
    private var startDate: Date? = null

    override fun injectActivity() {
        (application as? BaseApplication)?.applicationComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)

        initAdapter()
        initSwipeToRefresh()
        observeMoviesList()
        fetchMovies()

    }

    private fun initAdapter() {
        adapter = MovieAdapter(ArrayList()) { movieId ->
            startMovieDetailsActivity(movieId)
        }
        movies_list_view.adapter = adapter
        movies_list_view.addOnScrollListener(object : EndlessRecyclerViewScrollListener(movies_list_view.layoutManager
                as LinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                if (isFilter) {
                    safeLet(startDate, endDate) { startDate, endDate ->
                        filterMovies(startDate, endDate, page)
                    }
                } else {
                    fetchMovies(page)
                }
            }
        })
    }

    private fun initSwipeToRefresh() {
        movies_swipe_refresh_layout.setOnRefreshListener {
            fetchMovies()
        }
    }

    private fun observeMoviesList() {
        viewModel.moviesLiveData.observe(this, Observer { moviesLiveData ->
            movies_swipe_refresh_layout.post {
                movies_swipe_refresh_layout.isRefreshing =
                        moviesLiveData.networkState.status == NetworkState.LOADING.status
            }
            when (moviesLiveData.networkState) {
                NetworkState.LOADED -> {
                    adapter.add(moviesLiveData.movies)
                    showEmptyList(moviesLiveData.movies?.isEmpty() ?: false)
                }
                NetworkState.LOADING -> {
                    // Loading
                }
                else -> {
                    showEmptyList(moviesLiveData.networkState.message != null, moviesLiveData.networkState.message)
                }
            }
        })

        viewModel.filterLiveData.observe(this, Observer { moviesLiveData ->
            movies_swipe_refresh_layout.post {
                movies_swipe_refresh_layout.isRefreshing =
                        moviesLiveData.networkState.status == NetworkState.LOADING.status
            }
            when (moviesLiveData.networkState) {
                NetworkState.LOADED -> {
                    adapter.add(moviesLiveData.movies)
                    showEmptyList(moviesLiveData.movies?.isEmpty() ?: false)
                }
                NetworkState.LOADING -> {
                    // Loading
                }
                else -> {
                    showEmptyList(moviesLiveData.networkState.message != null, moviesLiveData.networkState.message)
                }
            }
        })
    }

    private fun fetchMovies(pageNumber: Int = 1) {
        viewModel.fetchMovies(pageNumber)
        isFilter = false
        if (pageNumber == 1)
            adapter.clear()
    }

    private fun filterMovies(startDate: Date, endDate: Date, pageNumber: Int = 1) {
        viewModel.filterMovies(pageNumber, startDate, endDate)
        isFilter = true
        if (pageNumber == 1)
            adapter.clear()
    }

    private fun showEmptyList(show: Boolean, message: String? = null) {
        message?.let {
            toast(it)
            empty_list.text = it
        }
        if (show && adapter.getMovies().isEmpty()) {
            empty_list.visibility = View.VISIBLE
            movies_list_view.visibility = View.GONE
        } else {
            empty_list.visibility = View.GONE
            movies_list_view.visibility = View.VISIBLE
        }
    }

    private fun showRangeDateDialog() {
        val now = Calendar.getInstance()
        var datePickerDialog: DatePickerDialog? = null
        startDate?.let { startDate ->
            now.time = startDate
            datePickerDialog = DatePickerDialog.newInstance(
                    this@MovieListActivity,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            )
        } ?: run {
            datePickerDialog = DatePickerDialog.newInstance(
                    this@MovieListActivity,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            )
        }
        @Suppress("DEPRECATION")
        datePickerDialog?.show(fragmentManager, "DatePickerDialog")
    }

    private fun startMovieDetailsActivity(movieId: Int?) {
        val intent = Intent(this@MovieListActivity, MovieDetailsActivity::class.java)
        intent.putExtra(CommonUtils.MOVIE_ID_PUT_EXTRA, movieId)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.movie_list_item_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            showRangeDateDialog()
            true
        }
        R.id.action_undo -> {
            fetchMovies()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int,
                           yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int) {
        startDate = Dates.of(year, monthOfYear, dayOfMonth)
        endDate = Dates.of(yearEnd, monthOfYearEnd, dayOfMonthEnd)
        safeLet(startDate, endDate) { startDate, endDate ->
            filterMovies(startDate, endDate)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.cleanObservables()
    }
}
