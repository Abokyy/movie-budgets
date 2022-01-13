package hu.benefanlabs.moviebudgets.domain.usecases

import hu.benefanlabs.moviebudgets.domain.IUseCase
import hu.benefanlabs.moviebudgets.domain.entities.Movie

interface GetPopularMoviesWithBudgetUseCase : IUseCase<Unit, List<Movie>>