package com.example.androidxmovies.ui.popularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.androidxmovies.data.api.MovieDBInterface
import com.example.androidxmovies.data.api.POST_PER_PAGE
import com.example.androidxmovies.data.repository.MovieDataSource
import com.example.androidxmovies.data.repository.MovieDataSourceFactory
import com.example.androidxmovies.data.repository.NetworkState
import com.oxcoding.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService : MovieDBInterface) {

    lateinit var moviePagedList:LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePageList(compositeDisposable: CompositeDisposable):LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService,compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory,config).build()

        return moviePagedList
    }

    fun getNetworkState():LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource , NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }
}