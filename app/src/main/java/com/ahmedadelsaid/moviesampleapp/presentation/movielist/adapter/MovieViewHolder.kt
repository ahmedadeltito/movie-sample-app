package com.ahmedadelsaid.moviesampleapp.presentation.movielist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmedadelsaid.moviesampleapp.R
import com.ahmedadelsaid.moviesampleapp.domain.model.Movie
import com.ahmedadelsaid.moviesampleapp.utils.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(movie: Movie?) {

        itemView.title.text = movie?.title
        itemView.release.text = String.format("Released on %s", movie?.releaseDate)
        itemView.votes.text = String.format("Total votes %d", movie?.voteCount)
        itemView.ratings.rating = (movie?.voteAverage?.toFloat() ?: 1F / 2)
        val imageUrl = "https://image.tmdb.org/t/p/w200/" + movie?.backdropPath
        GlideApp.with(itemView.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOption)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(itemView.preview)

    }

    companion object {
        val requestOption = RequestOptions().placeholder(R.drawable.ic_launcher_background).centerCrop()

        fun create(parent: ViewGroup): MovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            return MovieViewHolder(view)
        }
    }

}