package com.example.mvvmarchitecturestudy.data.repository.localDataSource

import androidx.lifecycle.LiveData
import com.example.mvvmarchitecturestudy.data.model.MovieModelResult
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    suspend fun saveMovie(movieModelResult: MovieModelResult)
    fun getSavedMovies() : Flow<List<MovieModelResult>>
    suspend fun deleteSavedMovie(movieModelResult : MovieModelResult)
    fun getSearchSavedMovies(keyword : String) : LiveData<List<MovieModelResult>>

}