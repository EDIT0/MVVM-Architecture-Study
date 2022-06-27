package com.example.mvvmarchitecturestudy.domain.repository

import androidx.lifecycle.LiveData
import com.example.mvvmarchitecturestudy.data.model.MovieModel
import com.example.mvvmarchitecturestudy.data.model.MovieModelResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesRepository {

    suspend fun getPopularMovies(language : String, page : Int) : Response<MovieModel>
    suspend fun getSearchMovies(query : String, language: String, page: Int) : Response<MovieModel>

    suspend fun saveMovie(movieModelResult: MovieModelResult)
    suspend fun deleteSavedMovie(movieModelResult: MovieModelResult)
    fun getSavedMovies() : Flow<List<MovieModelResult>>
    fun getSearchSavedMovies(keyword : String) : LiveData<List<MovieModelResult>>

}