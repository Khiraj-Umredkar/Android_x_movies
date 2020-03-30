package com.example.androidxmovies.ui.singleMovieDetail

import androidx.lifecycle.LiveData
import com.example.androidxmovies.data.api.MovieDBInterface
import com.example.androidxmovies.data.repository.MovieDetailsNetworkDataSource
import com.example.androidxmovies.data.repository.NetworkState
import com.example.androidxmovies.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService:MovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetail(compositeDisposable: CompositeDisposable,movieId:Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetail(movieId)

        return movieDetailsNetworkDataSource.downloadMovieDetailResponse

    }

    fun getMovieDetailsNetworkState():LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}