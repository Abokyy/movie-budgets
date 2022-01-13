package hu.benefanlabs.moviebudgets.data.network

import hu.benefanlabs.moviebudgets.data.network.entities.MovieDto
import hu.benefanlabs.moviebudgets.data.network.responses.MovieDtoListResponse
import io.ktor.client.request.*

class TmdbServiceImpl(
    apiClient: ApiClient
) : TmdbService {

    private val client = apiClient.createClient()

    override suspend fun getMovieById(movieId: Int): MovieDto {
        return client.get {
            url {
                encodedPath = "/movie/$movieId"
            }
            defaultApiKeyParameter()
        }
    }

    override suspend fun getPopularMovies(): MovieDtoListResponse {
        return client.get {
            url {
                encodedPath = "/movie/popular"
            }
            defaultApiKeyParameter()
        }
    }

    override suspend fun getNowPlayingMovies(): MovieDtoListResponse {
        return client.get {
            url {
                encodedPath = "/movie/now_playing"
            }
            defaultApiKeyParameter()
        }
    }

    override suspend fun searchForMovie(query: String): MovieDtoListResponse {
        return client.get {
            url {
                encodedPath = "/search/movie"
            }
            defaultApiKeyParameter()
            parameter("query", query)
        }
    }

    private fun HttpRequestBuilder.defaultApiKeyParameter() {
        this.parameter(API_KEY_PARAM_KEY, API_KEY_PARAM_VALUE)
    }

    companion object {
        const val API_KEY_PARAM_KEY = "api_key"
        const val API_KEY_PARAM_VALUE = "fcc41c30d01bfd3863a8829c73f51828"
    }
}