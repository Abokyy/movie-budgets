package hu.benefanlabs.moviebudgets.data.usecases

import hu.benefanlabs.moviebudgets.data.network.TmdbService
import hu.benefanlabs.moviebudgets.data.network.mappers.MovieDtoToMovieMapper
import hu.benefanlabs.moviebudgets.domain.UseCase
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.domain.usecases.GetPopularMoviesWithBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher

class DefaultGetPopularMoviesWithBudgetUseCase(
    private val service: TmdbService,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<Unit, List<Movie>>(coroutineDispatcher), GetPopularMoviesWithBudgetUseCase {
    override suspend fun execute(parameters: Unit): List<Movie> {
        val popularMoviesResponse = service.getPopularMovies()

        val moviesWithBudgetList = popularMoviesResponse.results.map {
            MovieDtoToMovieMapper().map(service.getMovieById(it.id))
        }

        return moviesWithBudgetList
    }
}