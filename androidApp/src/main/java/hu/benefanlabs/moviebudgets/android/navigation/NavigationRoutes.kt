package hu.benefanlabs.moviebudgets.android.navigation

sealed class NavigationRoutes(
    private val baseRoute: String,
    private val arguments: String? = null
) {
    val route = arguments?.let { "$baseRoute$arguments" } ?: baseRoute

    fun getNavLinkByArgument(arg: String): String {
        return "$baseRoute$arg"
    }

    object Splash : NavigationRoutes("splash")
    object MovieList : NavigationRoutes("movieList")
    object MovieDetails : NavigationRoutes("movie/", "{movieId}")
}