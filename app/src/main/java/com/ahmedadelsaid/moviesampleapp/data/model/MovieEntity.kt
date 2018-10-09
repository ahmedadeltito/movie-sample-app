package com.ahmedadelsaid.moviesampleapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * MovieEntity is the class that we will make our transactions by room.
 */

@Entity(tableName = "movie")
data class MovieEntity(

        @PrimaryKey
        @field:SerializedName("id")
        val id: Int? = null,

        @field:SerializedName("overview")
        val overview: String? = null,

        @field:SerializedName("original_language")
        val originalLanguage: String? = null,

        @field:SerializedName("original_title")
        val originalTitle: String? = null,

        @field:SerializedName("title")
        val title: String? = null,

        @field:SerializedName("release_date")
        val releaseDate: String? = null,

        @field:SerializedName("vote_average")
        val voteAverage: Double? = null,

        @field:SerializedName("popularity")
        val popularity: Double? = null,

        @field:SerializedName("vote_count")
        val voteCount: Int? = null,

        @field:SerializedName("backdrop_path")
        val backdropPath: String? = null
)