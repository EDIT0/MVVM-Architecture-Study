package com.example.mvvmarchitecturestudy.presentation.di

import com.example.mvvmarchitecturestudy.presentation.adapter.MovieAdapter
import com.example.mvvmarchitecturestudy.presentation.adapter.SavedMoviesAdapter
import com.example.mvvmarchitecturestudy.presentation.adapter.SearchMovieAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Provides
    @Singleton
    fun provideMovieAdapter() : MovieAdapter {
        return MovieAdapter()
    }

    @Provides
    @Singleton
    fun provideSearchMovieAdapter() : SearchMovieAdapter {
        return SearchMovieAdapter()
    }

    @Provides
    @Singleton
    fun provideSavedMoviesAdapter() : SavedMoviesAdapter {
        return SavedMoviesAdapter()
    }
}