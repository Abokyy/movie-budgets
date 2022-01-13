package hu.benefanlabs.moviebudgets.features

import hu.benefanlabs.core.mvi.AbstractMviView
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

abstract class MovieListView
    : AbstractMviView<
        MovieListViewModel.State,
        MovieListViewModel.Wish,
        MovieListViewModel.News>(),
    DIAware{

    override val di: DI by lazy { FeaturesModule }

    override val mViewModel: MovieListViewModel by instance()
}