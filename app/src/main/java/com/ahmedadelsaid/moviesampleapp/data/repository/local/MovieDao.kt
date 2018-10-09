package com.ahmedadelsaid.moviesampleapp.data.repository.local

import androidx.room.*
import com.ahmedadelsaid.moviesampleapp.data.model.MovieEntity
import io.reactivex.Single

/**
 * MovieEntity Dao Interface
 */

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie ORDER BY title ASC")
    val getMovies: Single<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovie(movieId: Int): Single<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(entity: MovieEntity)

    @Delete
    fun delete(entity: MovieEntity)

    @Query("DELETE FROM movie")
    fun deleteAll(): Int

}