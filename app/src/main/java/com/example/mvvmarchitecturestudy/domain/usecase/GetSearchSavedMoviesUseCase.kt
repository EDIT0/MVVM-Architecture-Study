package com.example.mvvmarchitecturestudy.domain.usecase

import androidx.lifecycle.LiveData
import com.example.mvvmarchitecturestudy.data.model.MovieModelResult
import com.example.mvvmarchitecturestudy.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetSearchSavedMoviesUseCase(
    private val moviesRepository: MoviesRepository
) {
    fun execute(keyword : String) : LiveData<List<MovieModelResult>> {
        return moviesRepository.getSearchSavedMovies(keyword)
    }
}