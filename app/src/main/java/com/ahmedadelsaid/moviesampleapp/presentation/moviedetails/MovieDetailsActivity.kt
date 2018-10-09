package com.ahmedadelsaid.moviesampleapp.presentation.moviedetails

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ahmedadelsaid.moviesampleapp.R
import com.ahmedadelsaid.moviesampleapp.base.BaseApplication
import com.ahmedadelsaid.moviesampleapp.base.ViewModelFactory
import com.ahmedadelsaid.moviesampleapp.domain.NetworkState
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import com.ahmedadelsaid.moviesampleapp.presentation.BaseActivity
import com.ahmedadelsaid.moviesampleapp.utils.CommonUtils
import com.ahmedadelsaid.moviesampleapp.utils.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * The movie details screen that is the entry point mostly of all the logic we have in this application.
 */

class MovieDetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var movieId: Int? = null

    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var requestOption: RequestOptions

    override fun injectActivity() {
        (application as? BaseApplication)?.applicationComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieId = intent?.extras?.getInt(CommonUtils.MOVIE_ID_PUT_EXTRA)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        requestOption = RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop()

        observeMovie()
        fetchMovie(movieId)

    }

    private fun observeMovie() {
        viewModel.movieLiveData.observe(this, Observer { movieItem ->
            when (movieItem.networkState) {
                NetworkState.LOADED -> {
                    initMovie(movieItem.movie)
                }
                NetworkState.LOADING -> {
                    // Loading
                }
                else -> {
                    movieItem.networkState.message?.let {
                        toast(it)
                    }
                }
            }
        })
    }

    private fun fetchMovie(movieId: Int?) {
        movieId?.let {
            viewModel.fetchMovie(it)
        }
    }

    private fun initMovie(movie: Movie?){
        movie_title.text = movie?.title
        release.text = String.format(resources.getString(R.string.release_on), movie?.releaseDate)
        overview.text = movie?.overview
        votes.text = String.format(resources.getString(R.string.total_vote), movie?.voteCount)
        ratings.rating = (movie?.voteAverage?.toFloat() ?: 1F / 2)
        val imageUrl = CommonUtils.IMAGE_BASE_URL + movie?.backdropPath
        GlideApp.with(this)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOption)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(preview)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.cleanObservables()
    }
}
