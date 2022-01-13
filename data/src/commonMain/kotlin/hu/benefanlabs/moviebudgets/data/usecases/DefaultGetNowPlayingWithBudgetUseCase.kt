package hu.benefanlabs.moviebudgets.data.usecases

import hu.benefanlabs.moviebudgets.data.network.TmdbService
import hu.benefanlabs.moviebudgets.data.network.mappers.MovieDtoToMovieMapper
import hu.benefanlabs.moviebudgets.domain.UseCase
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.domain.usecases.GetNowPlayingMoviesWithBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher

class DefaultGetNowPlayingWithBudgetUseCase(
    private val service: TmdbService,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<Unit, List<Movie>>(coroutineDispatcher), GetNowPlayingMoviesWithBudgetUseCase {
    override suspend fun execute(parameters: Unit): List<Movie> {
        val nowPlayingResponse = service.getNowPlayingMovies()

        val moviesWithBudgetList = nowPlayingResponse.results.map {
            MovieDtoToMovieMapper().map(service.getMovieById(it.id))
        }
        return moviesWithBudgetList
    }
}