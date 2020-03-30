package com.example.androidxmovies.ui.singleMovieDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.androidxmovies.data.repository.NetworkState
import com.example.androidxmovies.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepository: MovieDetailsRepository, movieId:Int):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetail: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetail(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}