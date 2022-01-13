package hu.benefanlabs.moviebudgets.data.di

import hu.benefanlabs.moviebudgets.data.network.ApiClient
import hu.benefanlabs.moviebudgets.data.network.TmdbService
import hu.benefanlabs.moviebudgets.data.network.TmdbServiceImpl
import hu.benefanlabs.moviebudgets.data.usecases.DefaultGetMovieByIdUseCase
import hu.benefanlabs.moviebudgets.data.usecases.DefaultGetNowPlayingWithBudgetUseCase
import hu.benefanlabs.moviebudgets.data.usecases.DefaultGetPopularMoviesWithBudgetUseCase
import hu.benefanlabs.moviebudgets.data.usecases.DefaultSearchForMoviesWithBudgetUseCase
import hu.benefanlabs.moviebudgets.domain.usecases.GetMovieByIdUseCase
import hu.benefanlabs.moviebudgets.domain.usecases.GetNowPlayingMoviesWithBudgetUseCase
import hu.benefanlabs.moviebudgets.domain.usecases.GetPopularMoviesWithBudgetUseCase
import hu.benefanlabs.moviebudgets.domain.usecases.SearchForMoviesWithBudgetUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val DataModule = DI {

    bind<CoroutineDispatcher>() with singleton { Dispatchers.Default }

    bind<ApiClient>() with singleton { ApiClient() }

    bind<TmdbService>() with singleton {
        TmdbServiceImpl(
            apiClient = instance()
        )
    }

    bind<GetMovieByIdUseCase>() with singleton {
        DefaultGetMovieByIdUseCase(
            service = instance(),
            coroutineDispatcher = instance()
        )
    }

    bind<GetNowPlayingMoviesWithBudgetUseCase>() with singleton {
        DefaultGetNowPlayingWithBudgetUseCase(
            service = instance(),
            coroutineDispatcher = instance()
        )
    }

    bind<GetPopularMoviesWithBudgetUseCase>() with singleton {
        DefaultGetPopularMoviesWithBudgetUseCase(
            service = instance(),
            coroutineDispatcher = instance()
        )
    }

    bind<SearchForMoviesWithBudgetUseCase>() with singleton {
        DefaultSearchForMoviesWithBudgetUseCase(
            service = instance(),
            coroutineDispatcher = instance()
        )
    }
}