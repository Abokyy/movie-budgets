package hu.benefanlabs.moviebudgets.domain.usecases

import hu.benefanlabs.moviebudgets.domain.IUseCase
import hu.benefanlabs.moviebudgets.domain.entities.Movie

interface SearchForMoviesWithBudgetUseCase : IUseCase<String?, List<Movie>>