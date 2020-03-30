package com.example.androidxmovies.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidxmovies.data.api.MovieDBInterface
import com.example.androidxmovies.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(private val apiService:MovieDBInterface , private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState:LiveData<NetworkState>
    get() = _networkState

    private val _downloadMovieDetailResponse = MutableLiveData<MovieDetails>()
    val  downloadMovieDetailResponse : LiveData<MovieDetails>
    get() = _downloadMovieDetailResponse

    fun fetchMovieDetail(movieId:Int){

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadMovieDetailResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailDataSource",it.message)
                        }
                    )
            )



        }catch (e:Exception){
            Log.e("MovieDetailDataSource",e.message+"")
            e.printStackTrace()
        }
    }
}