package hu.benefanlabs.moviebudgets.data.network.mappers

import hu.benefanlabs.moviebudgets.data.network.entities.MovieDto
import hu.benefanlabs.moviebudgets.domain.entities.Movie

class MovieDtoToMovieMapper : Mapper<MovieDto, Movie> {

    override fun map(from: MovieDto): Movie = from.run {
        Movie(
            id = id,
            title = title,
            posterPath = posterPath,
            budget = budget ?: 0,
            voteAverage = voteAverage
        )
    }
}