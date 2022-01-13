package hu.benefanlabs.moviebudgets.data.network.responses

import hu.benefanlabs.moviebudgets.data.network.entities.MovieDto
import kotlinx.serialization.Serializable

@Serializable
data class MovieDtoListResponse(
    val page: Int,
    val results: List<MovieDto>
)
