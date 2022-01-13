package hu.benefanlabs.moviebudgets.features

import hu.benefanlabs.core.mvi.BaseViewModel
import hu.benefanlabs.core.mvi.feature.ActorReducerNewsFeature
import hu.benefanlabs.core.mvi.feature.BaseFeature
import hu.benefanlabs.moviebudgets.data.di.DataModule
import hu.benefanlabs.moviebudgets.domain.entities.Movie
import hu.benefanlabs.moviebudgets.domain.exceptions.MovieBudgetsError
import hu.benefanlabs.moviebudgets.domain.usecases.GetMovieByIdUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class MovieDetailsViewModel(
    private val movieId: Int
) :
    BaseViewModel<Nothing, MovieDetailsViewModel.State, MovieDetailsViewModel.News>(),
    DIAware {

    override val di: DI by lazy { DataModule }

    private val getMovieByIdUseCase: GetMovieByIdUseCase by instance()

    data class State(
        val movie: Movie? = null,
        val loading: Boolean = false
    )

    sealed class Action {
        object GetMovieDetails : Action()
    }

    sealed class Effect {
        data class MovieDetailsGetSuccess(val movie: Movie) : Effect()
        data class MovieDetailsGetFailure(val error: MovieBudgetsError) : Effect()
        object Loading : Effect()
    }

    sealed class News {
        data class ShowError(val error: MovieBudgetsError) : News()
    }

    override val feature: ActorReducerNewsFeature<Action, Effect, State, News> =
        ActorReducerNewsFeature(
            initialState = State(loading = true),
            bootstrapper = {
                flowOf(Action.GetMovieDetails)
            },
            actor = { _, action ->
                when (action) {
                    Action.GetMovieDetails -> flow {
                        emit(Effect.Loading)
                        getMovieByIdUseCase.invoke(
                            parameters = movieId
                        ).foldResult(
                            onSuccess = {
                                emit(Effect.MovieDetailsGetSuccess(it))
                            },
                            onFailure = {
                                emit(Effect.MovieDetailsGetFailure(it))
                            }
                        )
                    }
                }
            },
            reducer = { state, effect ->
                when (effect) {
                    Effect.Loading -> state.copy(loading = true)
                    is Effect.MovieDetailsGetFailure -> state.copy(loading = false)
                    is Effect.MovieDetailsGetSuccess -> state.copy(
                        loading = false,
                        movie = effect.movie
                    )
                }
            },
            newsPublisher = { _, effect, _ ->
                when (effect) {
                    is Effect.MovieDetailsGetFailure -> News.ShowError(error = effect.error)
                    else -> null
                }
            }
        )
}