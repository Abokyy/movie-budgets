package hu.benefanlabs.moviebudgets.data.network.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    @SerialName("poster_path") val posterPath: String?,
    val budget: Int? = null,
    @SerialName("vote_average") val voteAverage: Double
)

