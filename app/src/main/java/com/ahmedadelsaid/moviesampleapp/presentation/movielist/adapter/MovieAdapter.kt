package com.ahmedadelsaid.moviesampleapp.presentation.movielist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie

/**
 * MovieAdapter is the movie adapter for movie list.
 */

class MovieAdapter(private var moviesList: ArrayList<Movie>, private val itemClickedCallback: (Int?) -> Unit)
    : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder.create(parent, itemClickedCallback)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(moviesList[position])
    }

    override fun getItemCount() = moviesList.size

    fun getMovies() = moviesList

    fun add(items: List<Movie>?) {
        items?.let {
            moviesList.addAll(it)
            val uniqueMoviesList = moviesList.distinctBy { movie ->
                movie.id
            }
            moviesList.clear()
            moviesList.addAll(uniqueMoviesList)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        moviesList.clear()
        notifyDataSetChanged()
    }
}