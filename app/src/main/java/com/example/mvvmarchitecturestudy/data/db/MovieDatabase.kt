package com.example.mvvmarchitecturestudy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmarchitecturestudy.data.model.MovieModelResult

@Database(
    entities = [MovieModelResult::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDAO() : MovieDAO
}