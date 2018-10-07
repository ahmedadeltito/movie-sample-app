package com.ahmedadelsaid.moviesampleapp.data.repository.local

import androidx.paging.DataSource
import androidx.room.*
import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import io.reactivex.Single

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie ORDER BY title ASC")
    val getMovies: DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Single<MovieEntity>

    @Query("SELECT * FROM movie WHERE title LIKE :title ORDER BY title ASC")
    fun searchMovies(title: String): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: MovieEntity)

    @Delete
    fun delete(entity: MovieEntity)

    @Query("DELETE FROM movie")
    fun deleteAll(): Int

}