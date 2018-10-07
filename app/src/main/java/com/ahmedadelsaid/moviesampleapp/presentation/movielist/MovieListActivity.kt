package com.ahmedadelsaid.moviesampleapp.presentation.movielist

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.ahmedadelsaid.moviesampleapp.R
import com.ahmedadelsaid.moviesampleapp.base.BaseApplication
import com.ahmedadelsaid.moviesampleapp.base.ViewModelFactory
import com.ahmedadelsaid.moviesampleapp.presentation.BaseActivity
import javax.inject.Inject

class MovieListActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieListViewModel::class.java)

    }

    override fun injectActivity() {
        (application as? BaseApplication)?.applicationComponent?.inject(this)
    }

}
