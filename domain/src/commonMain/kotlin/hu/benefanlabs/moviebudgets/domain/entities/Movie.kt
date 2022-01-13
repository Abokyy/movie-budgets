package hu.benefanlabs.moviebudgets.domain.entities

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val budget: Int,
    val voteAverage: Double
)
