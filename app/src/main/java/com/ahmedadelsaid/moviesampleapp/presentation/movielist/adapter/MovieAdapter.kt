package com.ahmedadelsaid.moviesampleapp.presentation.movielist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie

class MovieAdapter(private val moviesList: ArrayList<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder.create(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindTo(moviesList[position])
    }

    override fun getItemCount() = moviesList.size

    fun add(items: List<Movie>?) {
        items?.let {
            moviesList.addAll(items)
            val uniqueMoviesList = moviesList.distinctBy { movie ->
                movie.id
            }
            moviesList.clear()
            moviesList.addAll(uniqueMoviesList )
            notifyDataSetChanged()
        }
    }

    fun clear() {
        moviesList.clear()
        notifyDataSetChanged()
    }
}