package hu.benefanlabs.moviebudgets.features

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.multiton
import org.kodein.di.singleton


val FeaturesModule = DI {

    bind<MovieListViewModel>() with singleton {
        MovieListViewModel()
    }

    bind<MovieDetailsViewModel>() with multiton { movieId: Int ->
        MovieDetailsViewModel(movieId = movieId)
    }

}