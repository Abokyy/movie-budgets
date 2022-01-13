package hu.benefanlabs.moviebudgets.data.usecases

import hu.benefanlabs.moviebudgets.data.network.TmdbService
import hu.benefanlabs.moviebudgets.data.network.mappers.MovieDtoToMovieMapper
import hu.benefanlabs.moviebudgets.domain.UseCase
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.domain.usecases.SearchForMoviesWithBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher

class DefaultSearchForMoviesWithBudgetUseCase(
    private val service: TmdbService,
    coroutineDispatcher: CoroutineDispatcher
) : UseCase<String?, List<Movie>>(coroutineDispatcher), SearchForMoviesWithBudgetUseCase {

    override suspend fun execute(parameters: String?): List<Movie> {
        val searchListResponse = service.searchForMovie(parameters!!)

        val moviesWithBudgetList = searchListResponse.results.map {
            MovieDtoToMovieMapper().map(service.getMovieById(it.id))
        }

        return moviesWithBudgetList
    }
}