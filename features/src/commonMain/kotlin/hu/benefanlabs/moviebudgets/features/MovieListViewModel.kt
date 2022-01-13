package hu.benefanlabs.moviebudgets.features

import hu.benefanlabs.core.mvi.BaseViewModel
import hu.benefanlabs.core.mvi.feature.BaseFeature
import hu.benefanlabs.moviebudgets.data.di.DataModule
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.domain.exceptions.MovieBudgetsError
import hu.benefanlabs.moviebudgets.domain.usecases.GetNowPlayingMoviesWithBudgetUseCase
import hu.benefanlabs.moviebudgets.domain.usecases.GetPopularMoviesWithBudgetUseCase
import hu.benefanlabs.moviebudgets.domain.usecases.SearchForMoviesWithBudgetUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MovieListViewModel :
    BaseViewModel<MovieListViewModel.Wish, MovieListViewModel.State, MovieListViewModel.News>(),
    DIAware {

    override val di: DI by lazy { DataModule }

    private val getPopularMoviesWithBudgetUseCase: GetPopularMoviesWithBudgetUseCase by instance()
    private val getNowPlayingMoviesWithBudgetUseCase: GetNowPlayingMoviesWithBudgetUseCase by instance()
    private val searchedMoviesList: SearchForMoviesWithBudgetUseCase by instance()

    sealed class Wish {
        object SearchForMovies : Wish()
        data class SetQueryString(val queryString: String) : Wish()
    }

    data class State(
        val loading: Boolean,
        val popularMoviesList: List<Movie>? = null,
        val nowPlayingMoviesList: List<Movie>? = null,
        val searchedMoviesList: List<Movie>? = null,
        val queryString: String? = null
    )

    sealed class Action {
        object GetPopularMoviesList : Action()
        object GetNowPlayingMoviesList : Action()
        object SearchForMovies : Action()
        data class SetQueryString(val queryString: String) : Action()
    }

    sealed class Effect {
        data class PopularMoviesListGetSuccess(val popularMovies: List<Movie>) : Effect()
        data class PopularMoviesListGetFailure(val error: MovieBudgetsError) : Effect()
        data class NowPlayingMoviesListGetSuccess(val nowPlayingMovies: List<Movie>) : Effect()
        data class NowPlayingMoviesListGetFailure(val error: MovieBudgetsError) : Effect()
        data class SearchForMoviesSuccess(val searchedMovies: List<Movie>) : Effect()
        object EmptySearchResult : Effect()
        data class SearchForMoviesFailure(val error: MovieBudgetsError) : Effect()
        data class QueryStringSet(val queryString: String) : Effect()
        object Loading : Effect()
    }

    sealed class News {
        data class ShowError(val error: MovieBudgetsError) : News()
        object EmptySearchResult : News()
    }

    override val feature: BaseFeature<Wish, Action, Effect, State, News> =
        BaseFeature(
            initialState = State(loading = true),
            bootstrapper = {
                flowOf(Action.GetNowPlayingMoviesList, Action.GetPopularMoviesList)
            },
            wishToAction = { wish ->
                when (wish) {
                    Wish.SearchForMovies -> Action.SearchForMovies
                    is Wish.SetQueryString -> Action.SetQueryString(queryString = wish.queryString)
                }
            },
            actor = { state, action ->
                when (action) {
                    Action.GetNowPlayingMoviesList -> flow {
                        emit(Effect.Loading)
                        getNowPlayingMoviesWithBudgetUseCase.invoke(Unit)
                            .foldResult(
                                onSuccess = { movieList ->
                                    emit(Effect.NowPlayingMoviesListGetSuccess(movieList))

                                },
                                onFailure = { error ->
                                    emit(Effect.NowPlayingMoviesListGetFailure(error))
                                }
                            )
                    }
                    Action.GetPopularMoviesList -> flow {
                        emit(Effect.Loading)
                        getPopularMoviesWithBudgetUseCase.invoke(Unit)
                            .foldResult(
                                onSuccess = { movieList ->
                                    emit(Effect.PopularMoviesListGetSuccess(movieList))
                                },
                                onFailure = { error ->
                                    emit(Effect.PopularMoviesListGetFailure(error))
                                }
                            )
                    }
                    is Action.SearchForMovies -> flow {
                        emit(Effect.Loading)
                        searchedMoviesList.invoke(
                            parameters = state.queryString
                        ).foldResult(
                            onSuccess = {
                                if (it.isEmpty()) {
                                    emit(Effect.EmptySearchResult)
                                } else {
                                    emit(Effect.SearchForMoviesSuccess(it))
                                }
                            },
                            onFailure = {
                                emit(Effect.SearchForMoviesFailure(it))
                            }
                        )
                    }
                    is Action.SetQueryString -> flowOf(Effect.QueryStringSet(queryString = action.queryString))
                }
            },
            reducer = { state, effect ->
                when (effect) {
                    Effect.Loading -> state.copy(loading = true)
                    is Effect.NowPlayingMoviesListGetSuccess -> state.copy(
                        loading = false,
                        nowPlayingMoviesList = effect.nowPlayingMovies
                    )
                    is Effect.PopularMoviesListGetSuccess -> state.copy(
                        loading = false,
                        popularMoviesList = effect.popularMovies.shuffled()
                    )
                    is Effect.QueryStringSet -> state.copy(queryString = effect.queryString)
                    is Effect.SearchForMoviesSuccess -> state.copy(
                        loading = false,
                        searchedMoviesList = effect.searchedMovies
                    )
                    else -> state.copy(loading = false)
                }
            },
            newsPublisher = { _, effect, _ ->
                when (effect) {
                    is Effect.NowPlayingMoviesListGetFailure -> News.ShowError(error = effect.error)
                    is Effect.PopularMoviesListGetFailure -> News.ShowError(error = effect.error)
                    is Effect.SearchForMoviesFailure -> News.ShowError(error = effect.error)
                    Effect.EmptySearchResult -> News.EmptySearchResult
                    else -> null
                }
            }
        )
}