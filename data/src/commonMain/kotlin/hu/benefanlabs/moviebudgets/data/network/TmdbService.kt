package hu.benefanlabs.moviebudgets.data.network

import hu.benefanlabs.moviebudgets.data.network.entities.MovieDto
import hu.benefanlabs.moviebudgets.data.network.responses.MovieDtoListResponse

interface TmdbService {

    suspend fun getMovieById(movieId: Int): MovieDto
    suspend fun getPopularMovies(): MovieDtoListResponse
    suspend fun getNowPlayingMovies(): MovieDtoListResponse
    suspend fun searchForMovie(query: String): MovieDtoListResponse
}