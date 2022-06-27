package com.example.mvvmarchitecturestudy.presentation.di

import com.example.mvvmarchitecturestudy.domain.repository.MoviesRepository
import com.example.mvvmarchitecturestudy.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository) : GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(moviesRepository)
    }

    @Provides
    @Singleton
    fun provideGetSearchMoviesUseCase(moviesRepository: MoviesRepository) : GetSearchMoviesUseCase {
        return GetSearchMoviesUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideSaveMovieUseCase(moviesRepository: MoviesRepository) : SaveMovieUseCase {
        return SaveMovieUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedMoviesUseCase(moviesRepository: MoviesRepository) : GetSavedMoviesUseCase {
        return GetSavedMoviesUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteSavedMovieUseCase(moviesRepository: MoviesRepository) : DeleteSavedMovieUseCase {
        return DeleteSavedMovieUseCase(moviesRepository)
    }

    @Singleton
    @Provides
    fun provideGetSearchSavedMoviesUseCase(moviesRepository: MoviesRepository) : GetSearchSavedMoviesUseCase {
        return GetSearchSavedMoviesUseCase(moviesRepository)
    }
}