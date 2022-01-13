package hu.benefanlabs.moviebudgets.data.usecases

import hu.benefanlabs.moviebudgets.data.network.TmdbService
import hu.benefanlabs.moviebudgets.data.network.mappers.MovieDtoToMovieMapper
import hu.benefanlabs.moviebudgets.domain.UseCase
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.domain.usecases.GetMovieByIdUseCase
import kotlinx.coroutines.CoroutineDispatcher

class DefaultGetMovieByIdUseCase(
    private val service: TmdbService,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<Int, Movie>(coroutineDispatcher), GetMovieByIdUseCase {

    override suspend fun execute(parameters: Int): Movie {
        val movieResponse = service.getMovieById(parameters)

        return MovieDtoToMovieMapper().map(movieResponse)
    }
}