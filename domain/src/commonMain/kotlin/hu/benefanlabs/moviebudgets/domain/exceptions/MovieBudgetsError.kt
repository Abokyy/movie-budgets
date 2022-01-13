package hu.benefanlabs.moviebudgets.domain.exceptions


sealed class MovieBudgetsError {
    object UnknownError: MovieBudgetsError()
}

fun Throwable.toMovieBudgetsError(): MovieBudgetsError = when (this) {
    else -> MovieBudgetsError.UnknownError
}